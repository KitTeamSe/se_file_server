package com.se.fileserver.v1.file.adapter.presenter;

import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FilePresenterFormater implements FilePresenter {

  @Override
  public ResponseEntity<Resource> downloadFile(FileDownloadDto fileDownloadDto) {
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(fileDownloadDto.getFileType()))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + fileDownloadDto.getOriginalName() + "\"")
        .body(fileDownloadDto.getResource());
  }
}
