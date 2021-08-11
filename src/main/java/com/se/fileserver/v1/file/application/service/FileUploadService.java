package com.se.fileserver.v1.file.application.service;

import com.se.fileserver.v1.common.domain.exception.AttachmentTooLargeException;
import com.se.fileserver.v1.file.application.dto.FileUploadDto;
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
import java.util.stream.Collectors;
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
  private Path uploadDir;

  /* 단일, 다중 */
  @Transactional
  public List<FileUploadDto> upload(List<MultipartFile> multipartFiles, String service) {

    for (MultipartFile multipartFile : multipartFiles) {
      checkFileCondition(multipartFile);
    }

    Path targetLocation = getTargetLocation(service);

    List<File> fileEntityList = new ArrayList<>();
    for (MultipartFile multipartFile : multipartFiles) {
      fileEntityList.add(createFileEntity(multipartFile, targetLocation, service));
    }

    fileRepository.saveAll(fileEntityList);

    return fileEntityList.stream()
        .map(fileEntlty -> FileUploadDto.of(fileEntlty))
        .collect(Collectors.toList());
  }

  /* 단일 */
  @Transactional
  public FileUploadDto uploadOne(MultipartFile multipartFile, String service) {
    checkFileCondition(multipartFile);
    Path targetLocation = getTargetLocation(service);
    File fileEntity = createFileEntity(multipartFile, targetLocation, service);
    fileRepository.save(fileEntity);

    return FileUploadDto.of(fileEntity);
  }

  /* 파일이 저장될 경로 */
  private Path getTargetLocation(String service) {
    Path targetLocation = this.uploadDir;
    targetLocation = ensureUploadDirectory(targetLocation, service);
    return targetLocation;
  }

  /* 파일 객체 생성 */
  private File createFileEntity(MultipartFile multipartFile, Path targetLocation, String service) {

    String fileType = getMimeType(multipartFile);
    String originalName = multipartFile.getOriginalFilename();
    Long size = multipartFile.getSize();
    String saveName;
    Path storedLocation = targetLocation;
    do {
      saveName = createSaveName(getExtension(multipartFile));
    } while (isSameSaveNameExistsInRepository(saveName) || isSameSaveNameExistsInStorage(storedLocation.resolve(saveName)));

    String downloadUrl = createDownloadUrl(saveName);

    File newFile = new File(
        downloadUrl,
        service,
        fileType,
        originalName,
        saveName,
        size
    );

    storedLocation = storedLocation.resolve(saveName);
    storeFile(multipartFile, storedLocation);

    return newFile;
  }

  /* 유효성 검증 : 파일명, 파일 크기 */
  private void checkFileCondition(MultipartFile multipartFile) {
    if (multipartFile == null) {
      throw new InvalidFileException("파일을 가져오지 못하였습니다.");
    }

    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

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

  /* 서버에 저장될 파일명 검증 */
  /* -- Repository : DataBase */
  private boolean isSameSaveNameExistsInRepository(String saveName) {
    return fileRepository.findBySaveName(saveName).isPresent();
  }
  /* -- Storage : 파일서버의 저장공간 */
  private boolean isSameSaveNameExistsInStorage(Path targetLocation) {
    return Files.exists(targetLocation);
  }

  /* Download 'uri' 생성 */
  private String createDownloadUrl(String saveName) {
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/file-server/")
        .path("/v1/")
        .path("/file/")
        .path(saveName)
        .toUriString();
  }

  /* saveName : UUID.확장자 생성 */
  private String createSaveName(String extension) {
    return UUID.randomUUID().toString().replace("-", "")
        + "." + extension;
  }

  /* 확장자 추출 */
  private String getExtension(MultipartFile file) {
    return FilenameUtils.getExtension(file.getOriginalFilename());
  }

  /* MIME type 추출 */
  private String getMimeType(MultipartFile file) {
    String content = file.getContentType();
    if (content == null) return "application/octet-stream";
    return content;
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

