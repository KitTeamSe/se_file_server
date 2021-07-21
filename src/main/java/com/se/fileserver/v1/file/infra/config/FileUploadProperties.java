package com.se.fileserver.v1.file.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "se-file-server")
public class FileUploadProperties {

  private String uploadDir;
  private Long maxFileSize;

  public String getUploadDir() {
    return uploadDir;
  }

  public void setUploadDir(String uploadDir) {
    this.uploadDir = uploadDir;
  }
}
