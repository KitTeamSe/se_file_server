package com.se.fileserver.v1.file.infra.repository;

import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileJpaRepository extends JpaRepository<File,Long>, FileRepositoryProtocol {
  File findBySaveName(String saveName);

  @Override
  void delete(File entity);
}
