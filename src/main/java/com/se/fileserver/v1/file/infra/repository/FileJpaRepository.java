package com.se.fileserver.v1.file.infra.repository;

import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileJpaRepository extends JpaRepository<File,Long>, FileRepositoryProtocol {
  // TODO: 파일 태스크
  File findBySaveName(String fileName);
}
