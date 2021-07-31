package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.Optional;

public interface FileRepositoryProtocol {

  File findBySaveName(String saveName);

  void delete(File entity);
}
