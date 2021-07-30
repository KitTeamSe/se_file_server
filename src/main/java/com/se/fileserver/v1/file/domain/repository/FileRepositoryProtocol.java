package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface FileRepositoryProtocol{

  File save(File file);
  List<File> saveAll(List<File> files);
//  Page<File> findAllByService(String service);
}
