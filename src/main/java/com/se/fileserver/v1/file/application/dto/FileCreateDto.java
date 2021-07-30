package com.se.fileserver.v1.file.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileCreateDto {

  private String downloadUrl;

  public FileCreateDto(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

}
