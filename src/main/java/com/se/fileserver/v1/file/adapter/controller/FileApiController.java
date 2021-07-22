package com.se.fileserver.v1.file.adapter.controller;

import com.se.fileserver.v1.common.application.dto.SuccessResponse;
import com.se.fileserver.v1.file.application.service.FileUploadService;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.infra.dto.FileDto;
import com.se.fileserver.v1.file.infra.dto.FileDto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file-server/v1")
@Api(tags = "파일")
public class FileApiController {

  private final FileUploadService fileUploadService;

  public FileApiController(
      FileUploadService fileUploadService) {
    this.fileUploadService = fileUploadService;
  }

  @ApiOperation("파일 업로드")
  @PostMapping("/file")
  @ResponseStatus(value = HttpStatus.CREATED)
  public SuccessResponse<File> uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam String service) {
    return new SuccessResponse<>(HttpStatus.CREATED.value(), "파일 업로드 성공", fileUploadService.upload(file, service));
  }

  // 파일 멀티 업로드 (파일 업로드 모듈 사용)
//  @PostMapping("/files")
//  public SuccessResponse uploadMultiFile(@RequestBody List<FileDto.Request> request) {
//    return new SuccessResponse(HttpStatus.OK.value(), "멀티 파일 업로드 성공", fileUploadService.multiUpload(request));
//  }

  // 파일 다운로드 (url return해주기)

  // 파일 삭제

}
