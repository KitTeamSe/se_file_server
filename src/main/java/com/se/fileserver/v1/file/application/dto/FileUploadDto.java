package com.se.fileserver.v1.file.application.dto;

import com.se.fileserver.v1.file.domain.model.File;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileUploadDto {

  private String downloadUrl;
  private String originalName;
  //

  public FileUploadDto(String downloadUrl, String originalName) {
    this.downloadUrl = downloadUrl;
    this.originalName = originalName;
  }

  public static FileUploadDto of(File file) {
    return new FileUploadDto(file.getDownloadUrl(), file.getOriginalName());
  }

}
