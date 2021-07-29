package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.infra.repository.FileJpaRepository;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class FileRepository implements FileRepositoryProtocol{

  private final FileJpaRepository jpa;

  public FileRepository(FileJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public Optional<File> findBySaveName(String saveName) {
    return jpa.findBySaveName(saveName);
  }
}