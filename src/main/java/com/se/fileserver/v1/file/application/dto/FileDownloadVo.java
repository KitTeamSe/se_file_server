package com.se.fileserver.v1.file.application.dto;

import java.util.Objects;
import org.springframework.core.io.Resource;

// TODO: VO 위치 조정
public class FileDownloadVo {

  String originalName;
  Resource resource;
  String fileType;

  public FileDownloadVo(String originalName, Resource resource, String contentType) {
    this.originalName = originalName;
    this.resource = resource;
    this.fileType = contentType;
  }

  public String getOriginalName() {
    return originalName;
  }

  public Resource getResource() {
    return resource;
  }

  public String getFileType() {
    return fileType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileDownloadVo that = (FileDownloadVo) o;
    return Objects.equals(originalName, that.originalName) && Objects
        .equals(resource, that.resource) && Objects.equals(fileType, that.fileType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(originalName, resource, fileType);
  }
}
