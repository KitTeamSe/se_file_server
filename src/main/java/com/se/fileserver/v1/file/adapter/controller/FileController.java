package com.se.fileserver.v1.file.adapter.controller;

import com.se.fileserver.v1.common.domain.exception.BusinessException;
import com.se.fileserver.v1.file.application.dto.FileDownloadVo;
import com.se.fileserver.v1.file.application.dto.FileDto;
import com.se.fileserver.v1.file.application.service.FileDownloadService;
import com.se.fileserver.v1.file.application.service.error.FileErrorCode;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file-server/v1/")
public class FileController {
  // TODO: 파일 태스크
  private final FileDownloadService fileDownloadService;

  public FileController(FileDownloadService fileDownloadService) {
    this.fileDownloadService = fileDownloadService;
  }

  // 파일 업로드

  // 파일 멀티 업로드 (파일 업로드 모듈 사용)

  // 파일 다운로드
  @GetMapping("/{saveName:.+}")
  @ApiOperation(value = "파일 다운로드", notes = "파일 서버에 저장된 파일을 다운로드한다.")
  public ResponseEntity<Resource> downloadFile(@PathVariable String saveName) {
    // Load file as Resource
    FileDownloadVo fileDownloadVo = fileDownloadService.downloadFile(saveName);

    if (fileDownloadVo == null) {
      throw new BusinessException(FileErrorCode.FILE_DOES_NOT_EXISTS);
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(fileDownloadVo.getFileType()))
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDownloadVo.getOriginalName() + "\"")
        .body(fileDownloadVo.getResource());
  }

  // 파일 삭제

  // return SuccessResponse ? ResponseEntity ? --> VO DTO 에 물려있어서 우선 VO DTO 확실하게 맞추는게 먼저일
  // Repository듯 --> 도메인 내 Service에서는 Protocol을 바라보고, 인프라의 Repository는 다중상속을 받아서 사용 (인터페이스는 다중상속에 충돌나지 않음)
  // VO DTO

}
