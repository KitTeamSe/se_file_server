package com.se.fileserver.v1.file.application.dto;

import com.se.fileserver.v1.file.domain.model.File;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileReadDto {

  private Long fileId;
  private String downloadUrl;
  private String service;
  private String fileType;
  private String originalName;
  private String saveName;
  private Long size;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public FileReadDto(Long fileId, String downloadUrl, String service, String fileType,
      String originalName, String saveName, Long size, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.fileId = fileId;
    this.downloadUrl = downloadUrl;
    this.service = service;
    this.fileType = fileType;
    this.originalName = originalName;
    this.saveName = saveName;
    this.size = size;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static FileReadDto to(File file) {
    return builder()
        .fileId(file.getFileId())
        .downloadUrl(file.getDownloadUrl())
        .service(file.getService())
        .fileType(file.getFileType())
        .originalName(file.getOriginalName())
        .saveName(file.getSaveName())
        .size(file.getSize())

        .createdAt(file.getCreatedAt())
        .updatedAt(file.getUpdatedAt())
        .build();
  }
}
