package com.se.fileserver.v1.file.application.service;

import com.se.fileserver.v1.common.domain.exception.AttachmentTooLargeException;
import com.se.fileserver.v1.file.application.service.exception.FileStoreException;
import com.se.fileserver.v1.file.application.service.exception.InvalidFileException;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@Transactional(readOnly = true)
public class FileUploadService {

  private final FileRepositoryProtocol fileRepository;

  public FileUploadService(FileRepositoryProtocol fileRepository) {
    this.fileRepository = fileRepository;
  }

  @Value("${se-file-server.max-file-size}")
  private Long maxFileSize;
  @Value("${se-file-server.upload-dir}")
  private Path fileLocation;

  @Transactional
  public List<File> upload(List<MultipartFile> multipartFiles, String service) {

    Path targetLocation = this.fileLocation;
    targetLocation = ensureUploadDirectory(targetLocation, service);

    checkFileCondition(multipartFiles);

    List<File> fileEntityList = new ArrayList<>();

    for (MultipartFile multipartFile : multipartFiles) {

      String fileType = getExtension(multipartFile);
      String originalName = multipartFile.getOriginalFilename();
      Long size = multipartFile.getSize();
      String saveName;
      Path storedLocation = targetLocation;
      do {
        saveName = createSaveName(fileType);
      } while (isSameFileNameExists(storedLocation.resolve(saveName)));

      String downloadUri = createDownloadUri(saveName);

      File newFile = new File(
          downloadUri,
          service,
          fileType,
          originalName,
          saveName,
          size
      );

      storedLocation = storedLocation.resolve(saveName);
      fileRepository.save(newFile);
      storeFile(multipartFile, storedLocation);

      fileEntityList.add(newFile);
    }
    return fileEntityList;
  }

  /* 유효성 검증 : 파일명, 파일 크기 */
  private void checkFileCondition(List<MultipartFile> multipartFileList) {
    for (MultipartFile multipartFile : multipartFileList) {

      String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

      if (multipartFile == null) {
        throw new InvalidFileException("파일을 가져오지 못하였습니다.");
      }

      if (fileName.contains("..")) {
        throw new InvalidFileException("파일명에 [..]가 존재합니다 : " + fileName);
      }

      if (multipartFile.getSize() <= 0) {
        throw new InvalidFileException("파일 크기가 0보다 작습니다 : " + fileName);
      }

      if (multipartFile.getSize() >= maxFileSize) {
        throw new AttachmentTooLargeException("파일 크기가 " + maxFileSize + "를 초과합니다 : " + fileName);
      }

    }
  }

  /* 서버에 저장될 파일명 검증 */
  private boolean isSameFileNameExists(Path targetLocation) {
    return Files.exists(targetLocation);
  }

  /* Download 'uri' 생성 */
  private String createDownloadUri(String saveName) {
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/file/")
        .path("/download/")
        .path(saveName)
        .toUriString();
  }

  /* saveName : UUID.확장자 생성 */
  private String createSaveName(String fileType) {
    return UUID.randomUUID().toString().replace("-", "")
        + "." + fileType;
  }

  /* 확장자 추출 */
  private String getExtension(MultipartFile file) {
    return FilenameUtils.getExtension(file.getOriginalFilename());
  }

  /* 서버에 파일 저장 */
  private void storeFile(MultipartFile file, Path targetLocation) {
    try {
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
      throw new FileStoreException("파일을 업로드할 수 없습니다 : " + file.getOriginalFilename());
    }
  }

  /* 서비스에 해당하는 디렉토리가 존재함을 보장 */
  private Path ensureUploadDirectory(Path targetLocation, String directoryName) {
    try {
      Files.createDirectories(targetLocation.resolve(directoryName));
      targetLocation = targetLocation.resolve(directoryName);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("디렉토리 생성 오류");
    }

    return targetLocation;
  }
}

