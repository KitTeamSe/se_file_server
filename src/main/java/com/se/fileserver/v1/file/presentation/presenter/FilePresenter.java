package com.se.fileserver.v1.file.presentation.presenter;

import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.application.dto.FileReadDto;
import com.se.fileserver.v1.file.application.dto.FileUploadDto;
import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface FilePresenter {
  Response<List<FileUploadDto>> uploadFiles(List<FileUploadDto> files);
  Response<Pageable> readFiles(Page<FileReadDto> filePage);
  ResponseEntity<Resource> downloadFile(FileDownloadDto fileDownloadDto);
  Response<String> deleteFile();
  Response<FileUploadDto> uploadFile(FileUploadDto fileUploadDto);
}
