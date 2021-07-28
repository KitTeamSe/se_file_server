package com.se.fileserver.v1.file.adapter.controller;

import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.service.FileUploadService;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.presentation.FilePresenterFormatter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
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
  private final FilePresenterFormatter filePresenterFormatter;

  public FileApiController(
      FileUploadService fileUploadService,
      FilePresenterFormatter filePresenterFormatter) {
    this.fileUploadService = fileUploadService;
    this.filePresenterFormatter = filePresenterFormatter;
  }

  @ApiOperation("파일 업로드")
  @PostMapping("/file")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Response<File> uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam String service) {
    File uploadFile = fileUploadService.upload(file, service);
    return filePresenterFormatter.uploadFile(uploadFile);
  }

  @ApiOperation("파일 멀티 업로드")
  @PostMapping("/files")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Response<List<File>> uploadFiles(@RequestParam(value = "files") List<MultipartFile> files, @RequestParam String service) {
    List<File> uploadFlies = files.stream()
        .map(file -> fileUploadService.upload(file,service)).collect(Collectors.toList());
    return filePresenterFormatter.uploadFiles(uploadFlies);
  }

}
