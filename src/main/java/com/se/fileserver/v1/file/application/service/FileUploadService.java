package com.se.fileserver.v1.file.application.service;

import com.se.fileserver.v1.file.application.service.exception.FileUploadException;
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

    if (!isValidFile(file))
      throw new FileUploadException("유효하지 않은 파일명");

    String fileType = getExtension(file);
    String saveName = createSaveName() +"."+ fileType;
    String downloadUri = createDownloadUri(saveName);
    String originalName = file.getOriginalFilename();
    Long size = file.getSize();

    File newFile = new File(
        downloadUri,
        service,
        fileType,
        originalName,
        saveName,
        size
    );

    System.out.println("새로운 파일 생성 ");

    Path targetLocation = this.fileLocation.resolve(service).resolve(saveName);
    System.out.println(targetLocation.toString());
    System.out.println(newFile.getService());
    fileRepository.save(newFile);
    storeFile(file, targetLocation);

    return newFile;
  }

  /* 유효성 검증 : 파일명, 파일 크기 */
  public boolean isValidFile(MultipartFile file) {
    // file : user upload dir, 즉 요청된 파일의 .절대경로를 가져오는 것
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    if (file == null)
      throw new FileUploadException("파일을 가져오지 못함 ");
    if (fileName.contains(".."))
      throw new FileUploadException("파일명에 [..] 존재");

    if (file.getSize() <= 0)
      throw new FileUploadException("파일 크기가 0보다 작습니다.");

    if (file.getSize() >= maxFileSize)
      throw new FileUploadException("파일 크기가 최대 용량을 초과합니다.");

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

  /* saveName : UUID 생성 */
  private String createSaveName() {
    return UUID.randomUUID().toString().replace("-", "");
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
      throw new FileUploadException("파일을 업로드할 수 없습니다 : " + file.getOriginalFilename());
    }
  }
}

