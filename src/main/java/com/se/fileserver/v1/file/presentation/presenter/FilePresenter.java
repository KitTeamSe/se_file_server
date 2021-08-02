package com.se.fileserver.v1.file.presentation.presenter;

import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.presentation.response.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface FilePresenter {
  ResponseEntity<Resource> downloadFile(FileDownloadDto fileDownloadDto);

  Response<String> deleteFile();
}
