package com.se.fileserver.v1.file.presentation;

import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileCreateDto;
import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;

public interface FilePresenter {
  Response<File> uploadFile(File file);
  List<Response<FileCreateDto>> uploadFiles(List<File> files);
}
