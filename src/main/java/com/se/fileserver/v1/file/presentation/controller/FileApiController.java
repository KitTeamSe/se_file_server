package com.se.fileserver.v1.file.presentation.controller;

import com.se.fileserver.v1.common.application.dto.request.PaginationRequest;
import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.application.dto.FileUploadDto;
import com.se.fileserver.v1.file.application.dto.request.FileReadRequestDto;
import com.se.fileserver.v1.file.application.service.FileDeleteService;
import com.se.fileserver.v1.file.application.service.FileDownloadService;
import com.se.fileserver.v1.file.application.service.FileReadService;
import com.se.fileserver.v1.file.application.service.FileUploadService;
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
@Api(tags = "??????")
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

  @ApiOperation("?????? ????????? (?????? ???????????? ??????_????????????)")
  @PostMapping("/file")
  @ApiImplicitParam(name = "service", defaultValue = "se")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Response<FileUploadDto> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile, @RequestParam @NotNull String service) {
    FileUploadDto fileEntity = fileUploadService.uploadOne(multipartFile, service);
    return filePresenterFormatter.uploadFile(fileEntity);
  }

  /* (??????, ?????? ??????) */
  @ApiOperation("?????? ????????? (swagger????????? ????????? ?????????. PostMan??? ?????? ??????)")
  @PostMapping("/files")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Response<List<FileUploadDto>> uploadFiles(@RequestParam(value = "files") List<MultipartFile> multipartFiles, @RequestParam @NotNull String service) {
    List<FileUploadDto> fileEntityList = fileUploadService.upload(multipartFiles,service);
    return filePresenterFormatter.uploadFiles(fileEntityList);
  }

  /* ?????? ?????? ?????? */
  @ApiOperation("?????? ?????? ??????")
  @PostMapping("/file-list")
  @PreAuthorize("hasAuthority('SYSTEM')")
  @ResponseStatus(value = HttpStatus.OK)
  public Response<Pageable> readFiles(@RequestBody PaginationRequest<FileReadRequestDto> request) {
    PageImpl fileEntityList = fileReadService.readAll(request);
    return filePresenterFormatter.readFiles(fileEntityList);
  }

  /* ?????? ???????????? */
  @GetMapping("file/{saveName:.+}")
  @ApiImplicitParam(name = "saveName", value = "????????? ?????? ???(.????????? ??????)")
  @ApiOperation(value = "?????? ????????????", notes = "?????? ????????? ????????? ????????? ??????????????????.")
  public ResponseEntity<Resource> downloadFile(@PathVariable String saveName) {
    // Load file as Resource
    FileDownloadDto fileDownloadDto = fileDownloadService.downloadFile(saveName);

    if (fileDownloadDto == null) {
      throw new NotFoundException("????????? ???????????? ????????????.");
    }

    return filePresenterFormatter.downloadFile(fileDownloadDto);
  }


  /* ?????? ?????? */
  @DeleteMapping("file/{service}/{saveName:.+}")
  @ApiOperation(value = "?????? ??????", notes = "?????? ????????? ????????? ????????? ????????????.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "service", value = "?????????(se or pickple)"),
      @ApiImplicitParam(name = "saveName", value = "????????? ?????? ???")
  })
  public Response<String> deleteFile(@PathVariable String service, @PathVariable String saveName) {
    fileDeleteService.deleteFile(service, saveName);
    return filePresenterFormatter.deleteFile();
  }

}
