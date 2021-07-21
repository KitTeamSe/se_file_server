//package com.se.fileserver.v1.file.adapter.controller;
//
//import com.se.fileserver.v1.common.application.dto.SuccessResponse;
//import com.se.fileserver.v1.file.application.service.FileUploadService;
//import com.se.fileserver.v1.file.infra.dto.FileDto;
//import java.util.List;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/file-server/v1")// /file추가
//public class FileApiController {
//  // TODO: 파일 태스크
//  private FileUploadService fileUploadService;
//
//  // 파일 (멀티) 업로드
//  @PostMapping("/file")
//  public SuccessResponse<Long> uploadFile(@RequestBody FileDto.Request request) {
//    return new SuccessResponse<>(HttpStatus.OK.value(), "파일 업로드 성공", fileUploadService.upload(request));
//  }
//  // 파일 멀티 업로드 (파일 업로드 모듈 사용)
//  @PostMapping("/files")
//  public SuccessResponse uploadMultiFile(@RequestBody List<FileDto.Request> request) {
//    return new SuccessResponse(HttpStatus.OK.value(), "멀티 파일 업로드 성공", fileUploadService.multiUpload(request));
//  }
//
//  // 파일 다운로드 (url return해주기)
//
//  // 파일 삭제
//
//  // return SuccessResponse ? ResponseEntity ? --> VO DTO 에 물려있어서 우선 VO DTO 확실하게 맞추는게 먼저일
//  // Repository듯 --> 도메인 내 Service에서는 Protocol을 바라보고, 인프라의 Repository는 다중상속을 받아서 사용 (인터페이스는 다중상속에 충돌나지 않음)
//  // VO DTO
//
//}
