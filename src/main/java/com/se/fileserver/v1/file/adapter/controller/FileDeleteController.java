package com.se.fileserver.v1.file.adapter.controller;

import com.se.fileserver.v1.common.domain.exception.BusinessException;
import com.se.fileserver.v1.file.application.service.FileDeleteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file-server/v1/")
public class FileDeleteController {

  private final FileDeleteService fileDeleteService;

  public FileDeleteController(FileDeleteService fileDeleteService) {
    this.fileDeleteService = fileDeleteService;
  }

  // 파일 업로드

  // 파일 멀티 업로드 (파일 업로드 모듈 사용)

  // 파일 다운로드 (url return해주기)

  // 파일 삭제
  @DeleteMapping("/{saveName:.+}")
  @ApiOperation(value = "파일 삭제", notes = "파일을 삭제한다.")
  public String deleteFile(@PathVariable String saveName, String service) {
    return fileDeleteService.deleteFile(saveName, service);
  }

  // return SuccessResponse ? ResponseEntity ? --> VO DTO 에 물려있어서 우선 VO DTO 확실하게 맞추는게 먼저일
  // Repository듯 --> 도메인 내 Service에서는 Protocol을 바라보고, 인프라의 Repository는 다중상속을 받아서 사용 (인터페이스는 다중상속에 충돌나지 않음)
  // VO DTO

}
