package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.application.dto.FileCreateDto;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.infra.repository.FileJpaRepository;
import java.util.List;
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
  public File save(File file) {
    return jpa.save(file);
  }

  @Override
  public List<File> saveAll(List<File> files) {
    return jpa.saveAll(files);
  }


}
