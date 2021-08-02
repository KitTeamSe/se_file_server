package com.se.fileserver.v1.file.adapter.controller;

import com.se.fileserver.v1.common.application.dto.request.PaginationRequest;
import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileCreateDto;
import com.se.fileserver.v1.file.application.dto.FileReadDto;
//import com.se.fileserver.v1.file.application.service.FileReadService;
import com.se.fileserver.v1.file.application.dto.request.FileReadRequestDto;
import com.se.fileserver.v1.file.application.service.FileReadService;
import com.se.fileserver.v1.file.application.service.FileUploadService;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.presentation.FilePresenterFormatter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final FilePresenterFormatter filePresenterFormatter;

  public FileApiController(
      FileUploadService fileUploadService,
      FileReadService fileReadService,
      FilePresenterFormatter filePresenterFormatter) {
    this.fileUploadService = fileUploadService;
    this.fileReadService = fileReadService;
    this.filePresenterFormatter = filePresenterFormatter;
  }

  /* (단일, 다중 가능) */
  @ApiOperation("파일 업로드")
  @PostMapping("/file")
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<Response<FileCreateDto>> uploadFiles(@RequestParam(value = "files") List<MultipartFile> multipartFiles, @RequestParam @NotNull String service) {
    List<File> fileEntityList = fileUploadService.upload(multipartFiles,service);
    return filePresenterFormatter.uploadFiles(fileEntityList);
  }

  /* 파일 목록 조회 */
  @ApiOperation("파일 목록 조회")
  @PostMapping("/file-list")
  @ResponseStatus(value = HttpStatus.OK)
  public Response<Pageable> readFiles(@RequestBody PaginationRequest<FileReadRequestDto> request) {
    PageImpl fileEntityList = fileReadService.readAll(request);
    return filePresenterFormatter.readFiles(fileEntityList);
  }

}
