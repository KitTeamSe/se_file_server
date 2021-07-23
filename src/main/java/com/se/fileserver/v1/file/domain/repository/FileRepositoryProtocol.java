package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;

public interface FileRepositoryProtocol{
  File save(File file);

}
