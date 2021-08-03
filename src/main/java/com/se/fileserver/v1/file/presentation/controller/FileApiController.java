package com.se.fileserver.v1.file.presentation.controller;

import com.se.fileserver.v1.common.application.dto.request.PaginationRequest;
import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileUploadDto;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.application.dto.request.FileReadRequestDto;
import com.se.fileserver.v1.file.application.service.FileDeleteService;
import com.se.fileserver.v1.file.application.service.FileDownloadService;
import com.se.fileserver.v1.file.application.service.FileReadService;
import com.se.fileserver.v1.file.application.service.FileUploadService;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.presentation.presenter.FilePresenterFormatter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  private final FileReadService fileReadService;
  private final FileDownloadService fileDownloadService;
  private final FileDeleteService fileDeleteService;
  private final FilePresenterFormatter filePresenterFormatter;

  public FileApiController(
      FileUploadService fileUploadService,
      FileReadService fileReadService,
      FileDownloadService fileDownloadService,
      FileDeleteService fileDeleteService,
      FilePresenterFormatter filePresenterFormatter) {
    this.fileUploadService = fileUploadService;
    this.fileReadService = fileReadService;
    this.fileDownloadService = fileDownloadService;
    this.fileDeleteService = fileDeleteService;
    this.filePresenterFormatter = filePresenterFormatter;
  }

  @ApiOperation("파일 업로드 (단일 업로드만 가능_테스트용)")
  @PostMapping("/file")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Response<FileUploadDto> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile, @RequestParam @NotNull String service) {
    File fileEntity = fileUploadService.uploadOne(multipartFile, service);
    return filePresenterFormatter.uploadFile(fileEntity);
  }

  /* (단일, 다중 가능) */
  @ApiOperation("파일 업로드 (swagger에서는 테스트 불가능. PostMan등 사용 권장)")
  @PostMapping("/files")
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<Response<FileUploadDto>> uploadFiles(@RequestParam(value = "files") List<MultipartFile> multipartFiles, @RequestParam @NotNull String service) {
    List<File> fileEntityList = fileUploadService.upload(multipartFiles,service);
    return filePresenterFormatter.uploadFiles(fileEntityList);
  }

  /* 파일 목록 조회 */
  @ApiOperation("파일 목록 조회")
  @PostMapping("/file-list")
  @PreAuthorize("hasAuthority('SYSTEM')")
  @ResponseStatus(value = HttpStatus.OK)
  public Response<Pageable> readFiles(@RequestBody PaginationRequest<FileReadRequestDto> request) {
    PageImpl fileEntityList = fileReadService.readAll(request);
    return filePresenterFormatter.readFiles(fileEntityList);
  }

  /* 파일 다운로드 */
  @GetMapping("file/{saveName:.+}")
  @ApiImplicitParam(name = "saveName", value = "저장된 파일 명")
  @ApiOperation(value = "파일 다운로드", notes = "파일 서버에 저장된 파일을 다운로드한다.")
  public ResponseEntity<Resource> downloadFile(@PathVariable String saveName) {
    // Load file as Resource
    FileDownloadDto fileDownloadDto = fileDownloadService.downloadFile(saveName);

    if (fileDownloadDto == null) {
      throw new NotFoundException("파일이 존재하지 않습니다.");
    }

    return filePresenterFormatter.downloadFile(fileDownloadDto);
  }


  /* 파일 삭제 */
  @DeleteMapping("file/{service}/{saveName:.+}")
  @ApiOperation(value = "파일 삭제", notes = "파일 서버에 저장된 파일을 삭제한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "service", value = "서비스(se or pickple)"),
      @ApiImplicitParam(name = "saveName", value = "저장된 파일 명")
  })
  public Response<String> deleteFile(@PathVariable String service, @PathVariable String saveName) {
    fileDeleteService.deleteFile(service, saveName);
    return filePresenterFormatter.deleteFile();
  }

}
