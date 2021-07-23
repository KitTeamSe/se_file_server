package com.se.fileserver.v1.file.infra.dto;

import com.se.fileserver.v1.file.domain.model.File;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class FileVO {

  /* Response */
  public static class Response {

    private Long id;
    private String downloadUrl;
    private String service;
    private String fileType;
    private String originalName;
    private String saveName;
    private Long size;

    public Response() {
    }

    public Response(Long id, String downloadUrl, String service, String fileType,
        String originalName, String saveName, Long size) {
      this.id = id;
      this.downloadUrl = downloadUrl;
      this.service = service;
      this.fileType = fileType;
      this.originalName = originalName;
      this.saveName = saveName;
      this.size = size;
    }

    public Response toEntity(File file) {
      return new Response(file.getId(), file.getDownloadUrl(), file.getService(),
          file.getFileType(), file.getOriginalName(), file.getSaveName(), file.getSize());
    }

    public Long getId() {
      return id;
    }

    public String getDownloadUrl() {
      return downloadUrl;
    }

    public String getService() {
      return service;
    }

    public String getFileType() {
      return fileType;
    }

    public String getOriginalName() {
      return originalName;
    }

    public String getSaveName() {
      return saveName;
    }

    public Long getSize() {
      return size;
    }
  }
}
