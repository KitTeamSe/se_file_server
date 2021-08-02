package com.se.fileserver.v1.file.application.dto;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@Builder
public class FileDownloadDto {

  private String originalName;
  private Resource resource;
  private String fileType;

}
