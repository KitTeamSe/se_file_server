package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.Optional;

public interface FileRepositoryProtocol{
    // 주입 ?
  // TODO: 파일 태스크
  //void delete(File file); Service에서 불러올 것을 이부분에서 정의

  File save(File file);

}
