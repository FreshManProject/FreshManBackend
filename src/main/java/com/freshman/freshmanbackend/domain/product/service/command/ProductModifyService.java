package com.freshman.freshmanbackend.domain.product.service.command;

import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.domain.ProductImage;
import com.freshman.freshmanbackend.domain.product.domain.ProductSale;
import com.freshman.freshmanbackend.domain.product.request.ProductModifyRequest;
import com.freshman.freshmanbackend.domain.product.request.ProductSaleRequest;
import com.freshman.freshmanbackend.domain.product.service.query.ProductCategoryOneService;
import com.freshman.freshmanbackend.domain.product.service.query.ProductOneService;
import com.freshman.freshmanbackend.global.cloud.service.S3UploadService;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import com.freshman.freshmanbackend.global.common.utils.DateTimeUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 상품 수정 서비스
 *
 * 
 */
@Service
@RequiredArgsConstructor
public class ProductModifyService {
  private static final String THUMBNAIL_FOLDER = "thumbnail/";
  private static final String MAIN_IMAGE_FOLDER = "main-images/";
  private final ProductOneService productOneService;
  private final ProductCategoryOneService productCategoryOneService;
  private final S3UploadService s3UploadService;

  /**
   * 상품 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(ProductModifyRequest param) {
    String thumbnailPath;
    int order = 0;
    List<ProductImage> productImages = new ArrayList<>();

    // 상품 및 카테고리 조회
    Product product = productOneService.getOne(param.getProductSeq(), Boolean.TRUE);
    ProductCategory category = productCategoryOneService.getOne(param.getCategorySeq());

    // 상품 정보 수정
    product.update(param.getName(), param.getPrice(), param.getDescription(), param.getBrand(), category);

    //기존 메인 이미지 삭제
    s3UploadService.deleteAllFilesUnderPath(MAIN_IMAGE_FOLDER + product.getProductSeq() + "/");

    //이미지 변경
    MultipartFile thumbnailImage = param.getThumbnailImage();
    List<MultipartFile> mainImages = param.getMainImages();
    
    //썸네일 이미지 업로드
    try {
      thumbnailPath = s3UploadService.saveFile(thumbnailImage, THUMBNAIL_FOLDER + product.getProductSeq());
      product.setThumbnailImage(thumbnailPath);
    } catch (IOException e) {
      throw new ValidationException("s3.save_failed");
    }
    
    //메인 이미지 리스트 업로드
    for (MultipartFile file : mainImages){
      try {
        String fileUrl = s3UploadService.saveFile(file, MAIN_IMAGE_FOLDER + product.getProductSeq() + "/" + order);
        productImages.add(new ProductImage(fileUrl, order));
        order++;
      } catch (IOException e) {
        throw new ValidationException("s3.save_failed");
      }
    }
    
    //메인 이미지 업데이트
    product.updateImageList(productImages);
  }

  /**
   * 상품 할인정보 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(ProductSaleRequest param) {
    // 상품 할인정보 조회
    ProductSale sale = productOneService.getOne(param.getProductSeq(), Boolean.TRUE).getSale();

    // 할인정보 존재여부 검증
    verifySaleNotExists(sale);

    // 상품 할인정보 수정
    sale.update(param.getSalePrice(), DateTimeUtils.convertToDateTime(param.getSaleStartAt()),
        DateTimeUtils.convertToDateTime(param.getSaleEndAt()));
  }

  /**
   * 할인정보 존재여부 검증
   */
  private void verifySaleNotExists(ProductSale sale) {
    if (sale == null) {
      throw new ValidationException("product.sale.not_exists");
    }
  }
}
