package com.se.fileserver.v1.file.presentation;

import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileCreateDto;
import com.se.fileserver.v1.file.application.dto.FileReadDto;
import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilePresenter {
  List<Response<FileCreateDto>> uploadFiles(List<File> files);
  Response<Pageable> readFiles(Page<FileReadDto> filePage);
}
