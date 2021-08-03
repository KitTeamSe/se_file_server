package com.se.fileserver.v1.file.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileReadRequestDto {
  @ApiModelProperty(example = "se", notes = "서비스명(디렉토리명 : se, 330, pickple...)")
  private String service;

  public FileReadRequestDto(String service) {
    this.service = service;
  }
}
