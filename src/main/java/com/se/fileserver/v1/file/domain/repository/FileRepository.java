package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.infra.repository.FileJpaRepository;
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

//
//  @Override
//  public Page<File> findAllByService(String service) {
//    return jpa.findAllByService(service);
//  }


}
