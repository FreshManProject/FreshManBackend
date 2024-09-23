package com.freshman.freshmanbackend.global.cloud.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.freshman.freshmanbackend.global.cloud.domain.Image;
import com.freshman.freshmanbackend.global.cloud.repository.ImageRepository;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

/**
 * S3 서비스
 */
@Service
@RequiredArgsConstructor
public class S3UploadService {
  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  /**
   * S3 path에 파일 저장하고 그 파일의 URL 반환
   *
   * @param multipartFile
   * @return S3에 저장된 파일 URL
   * @throws IOException
   */
  public String saveFile(MultipartFile multipartFile, String path) throws IOException {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(multipartFile.getSize());
    metadata.setContentType(multipartFile.getContentType());
    String originalFilename = multipartFile.getOriginalFilename();
    if (originalFilename == null) {
      throw new ValidationException("s3.file_name_null");
    }
    String extension = extractExtension(originalFilename);
    String fileName = path + "." + extension;
    amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
    String url = amazonS3.getUrl(bucket, fileName).toString();
    return url;
  }

  /**
   * 폴더의 모든 파일 삭제
   * @param prefix
   */
  public void deleteAllFilesUnderPath(String prefix) {
    ObjectListing objectListing = amazonS3.listObjects(bucket, prefix);

    while (true) {
      for (S3ObjectSummary s3ObjectSummary : objectListing.getObjectSummaries()) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, s3ObjectSummary.getKey()));
      }
      if (objectListing.isTruncated()) {
        objectListing = amazonS3.listNextBatchOfObjects(objectListing);
      } else {
        break;
      }
    }
  }

  private String extractExtension(String originalFileName) {
    return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
  }
}
