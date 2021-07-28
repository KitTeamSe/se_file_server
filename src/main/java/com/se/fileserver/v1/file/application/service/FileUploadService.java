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

  @Value("${se-file-server.max-file-size}")
  private Long maxFileSize;
  @Value("${se-file-server.upload-dir}")
  private Path fileLocation;

  /* constructor */
  public FileUploadService(FileRepositoryProtocol fileRepository) {
    this.fileRepository = fileRepository;
  }

  @Transactional
  public File upload(MultipartFile file, String service) {

    Path targetLocation = this.fileLocation;
    try {
      Files.createDirectories(targetLocation.resolve(service));
      targetLocation = targetLocation.resolve(service);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("디렉토리 생성 오류");
    }

    if (!isValidFile(file))
      throw new InvalidFileException("업로드할 파일이 유효하지 않음");

    String fileType = getExtension(file);
    String originalName = file.getOriginalFilename();
    Long size = file.getSize();
    String saveName;
    do {
      saveName = createSaveName(fileType);
    } while (isSameFileNameExists(targetLocation.resolve(saveName)));
    String downloadUri = createDownloadUri(saveName);

    File newFile = new File(
        downloadUri,
        service,
        fileType,
        originalName,
        saveName,
        size
    );

    targetLocation = targetLocation.resolve(saveName);
    fileRepository.save(newFile);
    storeFile(file, targetLocation);

    return newFile;
  }

  /* 유효성 검증 : 파일명, 파일 크기 */
  private boolean isValidFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    if (file == null)
      throw new InvalidFileException("파일을 가져오지 못하였습니다.");

    if (fileName.contains(".."))
      throw new InvalidFileException("파일명에 [..]가 존재합니다.");

    if (file.getSize() <= 0)
      throw new InvalidFileException("파일 크기가 0보다 작습니다.");

    if (file.getSize() >= maxFileSize)
      throw new AttachmentTooLargeException("파일 크기가 " +maxFileSize + "를 초과합니다.");

    return true;
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
        +"."+fileType;
  }

  /* 확장자 추출 */
  private String getExtension(MultipartFile file) {
    return FilenameUtils.getExtension(file.getOriginalFilename());
  }

  /* 서버에 파일 저장 */
  private boolean storeFile(MultipartFile file, Path targetLocation) {
    try {
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      throw new FileStoreException("파일을 업로드할 수 없습니다 : " + file.getOriginalFilename());
    }
  }
}

