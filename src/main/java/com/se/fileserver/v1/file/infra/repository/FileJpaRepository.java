package com.se.fileserver.v1.file.infra.repository;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileJpaRepository extends JpaRepository<File,Long> {
  Optional<File> findBySaveName(String saveName);
  void delete(File file);
  Page<File> findAll(Pageable pageable);
  Page<File> findAllByService(Pageable pageable, String service);
}
