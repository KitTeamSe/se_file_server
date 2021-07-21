//package com.se.fileserver.v1.file.application.service;
//
//import com.se.fileserver.v1.file.application.service.exception.FileUploadException;
//import com.se.fileserver.v1.file.domain.model.File;
//import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
//import com.se.fileserver.v1.file.infra.config.FileUploadProperties;
//import com.se.fileserver.v1.file.infra.dto.FileDto;
//import com.se.fileserver.v1.file.infra.dto.FileDto.Request;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.UUID;
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//@Service
//@Transactional(readOnly = true)
//public class FileUploadService {
//
//  private FileRepositoryProtocol fileRepository;
//
//  @Value("${se-file-server.max-file-size}")
//  private Long maxFileSize;
//  @Value("${se-file-server.upload-dir}")
//  private Path fileLocation;
//
////  private FileUploadService(FileUploadProperties fileUploadProperties) {  // 파일이 올라가는 dir
////    this.fileLocation = Paths.get(fileUploadProperties.getUploadDir()).toAbsolutePath().normalize();
////    try {
////      Files.createDirectories(this.fileLocation);
////    } catch (IOException e) {
////      throw new
////    }
////    maxFileSize = prop.getMaxFileSize
////  }
//
//  @Transactional
//  public Long upload(FileDto.Request request) {
//
//    if (!isValidFile(request.getFile())) throw new FileUploadException("유효하지 않은 파일명");
//
//    String saveName = createSaveName();
//    String downloadUri = createDownloadUri(saveName);
//
//    File file = new File(
//        downloadUri,
//        request.getService(),
//        fileType,
//        originalName,
//        saveName,
//        size
//    );
//    return 1L;
//  }
//
//  /* 유효성 검증 : 파일명, 파일 크기 */
//  public boolean isValidFile(MultipartFile file) {
//    // file : user upload dir, 즉 요청된 파일의 .절대경로를 가져오는 것
//    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//    if (fileName.contains("..")) throw new FileUploadException("파일명에 [..] 존재");
//
//    if(file.getSize() <= 0)
//      throw new FileUploadException("파일 크기가 0보다 작습니다.");
//
//    if(file.getSize() >= maxFileSize)
//      throw new FileUploadException("파일 크기가 최대 용량을 초과합니다.");
//
//    return true;
//  }
//
//  /* 서버에 저장될 파일명 검증 */
//  private boolean isSameFileNameExists(Path targetLocation) {
//    return Files.exists(targetLocation);
//  }
//
//  /* Download 'uri' 생성 */
//  private String createDownloadUri(String saveName){
//    return  ServletUriComponentsBuilder.fromCurrentContextPath()
//        .path("/file/")
//        .path("/download/")
//        .path(saveName)
//        .toUriString();
//  }
//
//  /* saveName : UUID 생성 */
//  private String createSaveName() {
//    return UUID.randomUUID().toString().replace("-","");
//  }
//
//  /* 확장자 추출 */
//  private String getExtension(MultipartFile file) {
//    return FilenameUtils.getExtension(file.getOriginalFilename());
//  }
//
//}
