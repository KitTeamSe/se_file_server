package com.se.fileserver.v1.file.application.service;

import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.file.domain.exception.FileDeletionException;
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
    this.fileRepositoryProtocol = fileRepositoryProtocol;
    this.fileLocation = ensureDownloadDir(directory);
  }

  public void deleteFile(String service, String saveName) {
    Path filePath = this.fileLocation.resolve(service).resolve(saveName).normalize();
    java.io.File file = new java.io.File(filePath.toString());
    File fileModel = fileRepositoryProtocol.findBySaveName(saveName);

    if (!file.exists() || fileModel == null) {
      throw new NotFoundException("존재하지 않는 파일입니다.");
    }

    if (!file.delete()) {
      throw new FileDeletionException("파일 삭제 중 오류가 발생했습니다.");
    }

    fileRepositoryProtocol.delete(fileModel);
  }

  private Path ensureDownloadDir(String directory) {
    Path fileLocation;
    try {
      fileLocation = Paths.get(directory).toAbsolutePath().normalize();
      if (!Files.exists(fileLocation)) {
        throw new Exception();
      }
    } catch (Exception e) {
      throw new NotFoundException("존재하지 않는 파일 경로입니다.");
    }
    return fileLocation;
  }
}