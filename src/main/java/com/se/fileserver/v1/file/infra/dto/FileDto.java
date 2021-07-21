package com.se.fileserver.v1.file.infra.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileDto {


  public static class Request {
    private MultipartFile file;
    private String service;

    public Request(MultipartFile file, String service) {
      this.file = file;
      this.service = service;
    }

    public MultipartFile getFile() {
      return file;
    }

    public void setFile(MultipartFile file) {
      this.file = file;
    }

    public String getService() {
      return service;
    }

    public void setService(String service) {
      this.service = service;
    }
  }


}
