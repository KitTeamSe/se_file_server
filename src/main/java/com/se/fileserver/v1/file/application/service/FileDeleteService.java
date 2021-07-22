package com.se.fileserver.v1.file.application.service;

import com.se.fileserver.v1.common.domain.exception.BusinessException;
import com.se.fileserver.v1.file.application.service.error.FileErrorCode;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileDeleteService {
  private final Path fileLocation;

  private final FileRepositoryProtocol fileRepositoryProtocol;

  public FileDeleteService(@Value("${se-file-server.upload-dir}") String directory,
      FileRepositoryProtocol fileRepositoryProtocol) {
    this.fileLocation = Paths.get(directory).toAbsolutePath().normalize();
    this.fileRepositoryProtocol = fileRepositoryProtocol;

    try {
      Files.exists(this.fileLocation);
    }
    catch(Exception e) {
      // TODO: 파일 삭제에 대한 에러코드 정의
      throw new BusinessException(FileErrorCode.DOWNLOAD_PATH_DOES_NOT_EXISTS);
    }
  }

  public String deleteFile(String saveName, String service){
    Path filePath = this.fileLocation.resolve(service).resolve(saveName).normalize();
    java.io.File file = new java.io.File(filePath.toString());

    if (!file.exists()) {
      throw new BusinessException(FileErrorCode.FILE_DOES_NOT_EXISTS);
    }

    if (!file.delete()) {
      // 파일 삭제 실패했다는 enum 필요
      throw new BusinessException(FileErrorCode.FILE_DOES_NOT_EXISTS);
    }

    File fileModel = fileRepositoryProtocol.findBySaveName(saveName);

    if (fileModel == null) {
      throw new BusinessException(FileErrorCode.FILE_DOES_NOT_EXISTS);
    }

    fileRepositoryProtocol.delete(fileModel);
    // TODO: 파일 삭제 리턴 타입 정하기
    return "Deleted successfully.";
  }
}
