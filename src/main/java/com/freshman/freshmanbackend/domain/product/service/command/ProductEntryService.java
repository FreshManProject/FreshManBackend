package com.freshman.freshmanbackend.domain.product.service.command;

import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.domain.ProductImage;
import com.freshman.freshmanbackend.domain.product.domain.ProductSale;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;
import com.freshman.freshmanbackend.domain.product.request.ProductEntryRequest;
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
 * 상품 등록 서비스
 *
 * 
 */
@Service
@RequiredArgsConstructor
public class ProductEntryService {
  private static final String THUMBNAIL_FOLDER = "thumbnail/";
  private static final String MAIN_IMAGE_FOLDER = "main-images/";
  private final ProductRepository productRepository;
  private final ProductOneService productOneService;
  private final ProductCategoryOneService productCategoryOneService;
  private final S3UploadService s3UploadService;

  /**
   * 상품 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(ProductEntryRequest param) {
    String thumbnailUrl;
    List<ProductImage> imageList = new ArrayList<>();
    int imageOrder = 0;
    // 카테고리 조회
    ProductCategory category = productCategoryOneService.getOne(param.getCategorySeq());

    // 상품 등록
    Product product = productRepository.save(
            new Product(param.getName(), param.getPrice(), param.getDescription(), param.getBrand(), category));
    try {
      thumbnailUrl = s3UploadService.saveFile(param.getThumbnailImage(), THUMBNAIL_FOLDER + product.getProductSeq());
      product.setThumbnailImage(thumbnailUrl);
    } catch (IOException e) {
      throw new ValidationException("s3.save_failed");
    }
    for (MultipartFile file : param.getMainImages()){
      try {
        String url = s3UploadService.saveFile(file, MAIN_IMAGE_FOLDER + product.getProductSeq() + "/" + imageOrder);
        imageList.add(new ProductImage(url, imageOrder));
        imageOrder++;
      } catch (IOException e) {
        throw new ValidationException("s3.save_failed");
      }
    }
    product.addImageList(imageList);
  }

  /**
   * 상품 할인정보 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(ProductSaleRequest param) {
    // 상품 조회
    Product product = productOneService.getOne(param.getProductSeq(), Boolean.TRUE);

    // 할인정보 존재여부 검증
    verifySaleExists(product.getSale());

    // 상품 할인정보 등록
    product.addSale(new ProductSale(param.getSalePrice(), DateTimeUtils.convertToDateTime(param.getSaleStartAt()),
        DateTimeUtils.convertToDateTime(param.getSaleEndAt())));
  }

  /**
   * 할인정보 존재여부 검증
   */
  private void verifySaleExists(ProductSale sale) {
    if (sale != null) {
      throw new ValidationException("product.sale.already_exists");
    }
  }
}
