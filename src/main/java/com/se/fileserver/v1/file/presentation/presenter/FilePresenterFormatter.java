package com.se.fileserver.v1.file.presentation.presenter;

import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.presentation.response.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FilePresenterFormatter implements FilePresenter {


  @Override
  public ResponseEntity<Resource> downloadFile(FileDownloadDto fileDownloadDto) {
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(fileDownloadDto.getFileType()))
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDownloadDto.getOriginalName() + "\"")
        .body(fileDownloadDto.getResource());
  }

  @Override
  public Response<String> deleteFile() {
    return new Response<>(HttpStatus.OK, "파일 삭제를 완료했습니다.");
  }
}
