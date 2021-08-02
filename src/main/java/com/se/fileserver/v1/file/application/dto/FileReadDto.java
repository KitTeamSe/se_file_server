package com.se.fileserver.v1.file.application.dto;

import com.se.fileserver.v1.file.domain.model.File;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileReadDto {

  private Long fileId;
  private String downloadUrl;
  private String service;
  private String fileType;
  private String originalName;
  private String saveName;
  private Long size;

  public FileReadDto(Long fileId, String downloadUrl, String service, String fileType,
      String originalName, String saveName, Long size) {
    this.fileId = fileId;
    this.downloadUrl = downloadUrl;
    this.service = service;
    this.fileType = fileType;
    this.originalName = originalName;
    this.saveName = saveName;
    this.size = size;
  }

  public static FileReadDto to(File file) {
    return new FileReadDto(
        file.getFileId(),
        file.getDownloadUrl(),
        file.getService(),
        file.getFileType(),
        file.getOriginalName(),
        file.getSaveName(),
        file.getSize());
  }
}
