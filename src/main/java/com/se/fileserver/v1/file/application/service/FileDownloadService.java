package com.se.fileserver.v1.file.application.service;

import com.se.fileserver.v1.common.domain.exception.BusinessException;
import com.se.fileserver.v1.config.FileProperties;
import com.se.fileserver.v1.file.application.dto.FileDto;
import com.se.fileserver.v1.file.application.dto.FileDownloadVo;
import com.se.fileserver.v1.file.application.service.error.FileErrorCode;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class FileDownloadService {

  private final Path fileLocation;

  private final FileRepositoryProtocol fileRepositoryProtocol;

  public FileDownloadService(FileProperties prop,
      FileRepositoryProtocol fileRepositoryProtocol) {
    this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
    this.fileRepositoryProtocol = fileRepositoryProtocol;

    try {
      Files.exists(this.fileLocation);
    } catch (Exception e) {
      throw new BusinessException(FileErrorCode.DOWNLOAD_PATH_DOES_NOT_EXISTS);
    }
  }

  public FileDownloadVo downloadFile(String fileName) {
    try {
      File file = fileRepositoryProtocol.findBySaveName(fileName);

      if (file == null) {
        throw new BusinessException(FileErrorCode.FILE_DOES_NOT_EXISTS);
      }

      Path filePath = this.fileLocation.resolve(file.getService()).resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());

      // 리소스 없으면 예외처리
      if (!resource.exists()) {
        // TODO : 리소스가 없다 == 파일이 없다? 고민하기
        throw new BusinessException(FileErrorCode.FILE_DOES_NOT_EXISTS);
      }

      // 파일 타입 정의
      String fileType = file.getFileType();
      if (fileType == null) {
        fileType = "application/octet-stream";
      }

      // 한글 출력 문제 해결
      String originalFileName = new String(file.getOriginalName().getBytes(StandardCharsets.UTF_8),
          StandardCharsets.ISO_8859_1);

      System.out.println(file.getOriginalName());
      System.out.println(originalFileName);

      return new FileDownloadVo(originalFileName, resource, fileType);

    } catch (MalformedURLException e) {
      throw new BusinessException(FileErrorCode.FILE_DOES_NOT_EXISTS);
    }
  }

  public String getOriginalName(String saveName) {
    // DB에서 원본파일명으로 복구함.
    File file = fileRepositoryProtocol.findBySaveName(saveName);
    return file.getOriginalName();
  }
}