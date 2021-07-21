package com.se.fileserver.v1.file.application.dto;

import java.util.Objects;

public class FileVO {

  private Long id;
  private String downloadUrl;
  private String service;
  private String fileType;
  private String originalName;
  private String saveName;
  private Long size;

  public FileVO(Long id, String downloadUrl, String service, String fileType,
      String originalName, String saveName, Long size) {
    this.id = id;
    this.downloadUrl = downloadUrl;
    this.service = service;
    this.fileType = fileType;
    this.originalName = originalName;
    this.saveName = saveName;
    this.size = size;
  }

  public static FileVO of(Long id, String downloadUrl, String service, String fileType,
      String originalName, String saveName, Long size) {
    return new FileVO(id, downloadUrl, service, fileType, originalName, saveName, size);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileVO fileVO = (FileVO) o;
    return Objects.equals(id, fileVO.id) && Objects
        .equals(downloadUrl, fileVO.downloadUrl) && Objects.equals(service, fileVO.service)
        && Objects.equals(fileType, fileVO.fileType) && Objects
        .equals(originalName, fileVO.originalName) && Objects
        .equals(saveName, fileVO.saveName) && Objects.equals(size, fileVO.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, downloadUrl, service, fileType, originalName, saveName, size);
  }

}
