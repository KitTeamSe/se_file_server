package com.se.fileserver.v1.file.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileReadRequestDto {
  private String service;

  public FileReadRequestDto(String service) {
    this.service = service;
  }
}
