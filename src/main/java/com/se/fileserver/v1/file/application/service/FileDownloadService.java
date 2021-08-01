package com.se.fileserver.v1.file.application.service;

import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class FileDownloadService {

  private final Path fileLocation;

  private final FileRepositoryProtocol fileRepositoryProtocol;

  public FileDownloadService(@Value("${se-file-server.upload-dir}") String directory,
      FileRepositoryProtocol fileRepositoryProtocol) {
    this.fileRepositoryProtocol = fileRepositoryProtocol;
    this.fileLocation = Paths.get(directory).toAbsolutePath().normalize();
  }

  private Resource setResource(Path filePath) {
    Resource resource;
    try {
      resource = new UrlResource(filePath.toUri());
      // 리소스 없으면 예외처리
      if (!resource.exists()) {
        throw new NotFoundException("존재하지 않는 리소스입니다.");
      }
    } catch (MalformedURLException | NotFoundException e) {
      throw new NotFoundException(e.getMessage());
    }
    return resource;
  }

  public FileDownloadDto downloadFile(String saveName) {
    checkFileLocationExists();

    File file = fileRepositoryProtocol.findBySaveName(saveName);

    if (file == null) {
      throw new NotFoundException("존재하지 않는 파일입니다.");
    }

    Path filePath = this.fileLocation.resolve(file.getService()).resolve(saveName).normalize();
    Resource resource = setResource(filePath);

    // 파일 타입 정의
    String fileType = file.getFileType();
    if (fileType == null) {
      fileType = "application/octet-stream";
    }

    // 한글 출력 문제 해결
    String originalFileName = new String(file.getOriginalName().getBytes(StandardCharsets.UTF_8),
        StandardCharsets.ISO_8859_1);

    return FileDownloadDto.builder()
        .originalName(originalFileName)
        .resource(resource)
        .fileType(fileType)
        .build();
  }

  private void checkFileLocationExists() {
    if (!Files.exists(this.fileLocation)) {
      throw new NotFoundException("존재하지 않는 파일 경로입니다.");
    }
  }
}