package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FileRepositoryProtocol {
  File findBySaveName(String saveName);
  void delete(File file);
  List<File> saveAll(List<File> files);
  Page<File> findAll(Pageable pageable);
  Page<File> findAllByService(Pageable pageable, String service);
  File save(File file);
}
