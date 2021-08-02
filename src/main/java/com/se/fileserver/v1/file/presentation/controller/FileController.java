package com.se.fileserver.v1.file.presentation.controller;

import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.application.service.FileDeleteService;
import com.se.fileserver.v1.file.application.service.FileDownloadService;
import com.se.fileserver.v1.file.presentation.presenter.FilePresenter;
import com.se.fileserver.v1.file.presentation.response.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file-server/v1")
public class FileController {
  private final FileDownloadService fileDownloadService;
  private final FileDeleteService fileDeleteService;
  private final FilePresenter filePresenter;

  public FileController(FileDownloadService fileDownloadService,
      FileDeleteService fileDeleteService,
      FilePresenter filePresenter) {
    this.fileDownloadService = fileDownloadService;
    this.fileDeleteService = fileDeleteService;
    this.filePresenter = filePresenter;
  }

  // 파일 다운로드
  @GetMapping("/{saveName:.+}")
  @ApiImplicitParam(name = "saveName", value = "저장된 파일 명")
  @ApiOperation(value = "파일 다운로드", notes = "파일 서버에 저장된 파일을 다운로드한다.")
  public ResponseEntity<Resource> downloadFile(@PathVariable String saveName) {
    // Load file as Resource
    FileDownloadDto fileDownloadDto = fileDownloadService.downloadFile(saveName);

    if (fileDownloadDto == null) {
      throw new NotFoundException("파일이 존재하지 않습니다.");
    }

    return filePresenter.downloadFile(fileDownloadDto);
  }


  // 파일 삭제
  @DeleteMapping("/{service}/{saveName:.+}")
  @ApiOperation(value = "파일 삭제", notes = "파일 서버에 저장된 파일을 삭제한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "service", value = "서비스(se or pickple)"),
      @ApiImplicitParam(name = "saveName", value = "저장된 파일 명")
  })
  public Response<String> deleteFile(@PathVariable String service, @PathVariable String saveName) {
    fileDeleteService.deleteFile(service, saveName);
    return filePresenter.deleteFile();
  }
}
