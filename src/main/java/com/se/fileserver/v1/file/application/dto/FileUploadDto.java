package com.se.fileserver.v1.file.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileUploadDto {

  private String downloadUrl;

  public FileUploadDto(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

}
