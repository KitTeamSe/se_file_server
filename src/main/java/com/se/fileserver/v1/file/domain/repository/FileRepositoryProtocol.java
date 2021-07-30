package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;
import org.springframework.data.domain.Page;

public interface FileRepositoryProtocol{
  List<File> saveAll(List<File> files);
  //Page<File> findAllByService(String service);
}
