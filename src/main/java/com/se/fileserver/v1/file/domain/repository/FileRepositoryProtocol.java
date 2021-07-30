package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.application.dto.FileCreateDto;
import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;

public interface FileRepositoryProtocol{

  File save(File file);
  List<File> saveAll(List<File> files);
}
