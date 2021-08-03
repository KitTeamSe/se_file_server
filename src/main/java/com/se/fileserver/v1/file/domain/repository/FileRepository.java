package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.infra.repository.FileJpaRepository;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class FileRepository implements FileRepositoryProtocol{

  private final FileJpaRepository jpa;

  public FileRepository(FileJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public File findBySaveName(String saveName) {
    return jpa.findBySaveName(saveName);
  }

  @Override
  public void delete(File file) {
    jpa.delete(file);
  }

  @Override
  public List<File> saveAll(List<File> files) {
    return jpa.saveAll(files);
  }

  @Override
  public Page<File> findAll(Pageable pageable) {
    return jpa.findAll(pageable);
  }

  @Override
  public Page<File> findAllByService(Pageable pageable, String service) {
    return jpa.findAllByService(pageable, service);
  }

  @Override
  public File save(File file) {
    return jpa.save(file);
  }

}
