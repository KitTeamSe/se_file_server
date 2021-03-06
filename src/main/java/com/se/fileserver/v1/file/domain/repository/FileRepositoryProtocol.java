package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FileRepositoryProtocol {
  Optional<File> findBySaveName(String saveName);
  void delete(File file);
  List<File> saveAll(List<File> files);
  Page<File> findAll(Pageable pageable);
  Page<File> findAllByService(Pageable pageable, String service);
  File save(File file);
  Optional<File> findFirstByService(String service);
}
