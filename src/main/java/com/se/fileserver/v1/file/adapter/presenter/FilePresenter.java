package com.se.fileserver.v1.file.adapter.presenter;

import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface FilePresenter {

  ResponseEntity<Resource> downloadFile(FileDownloadDto fileDownloadDto);

}
