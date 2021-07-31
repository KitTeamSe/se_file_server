package com.se.fileserver.v1.file.infra.repository;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface FileJpaRepository extends JpaRepository<File,Long> {
  File findBySaveName(String saveName);

  void delete(File entity);
}
