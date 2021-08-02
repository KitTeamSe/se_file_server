package com.se.fileserver.v1.file.presentation.presenter;

import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileCreateDto;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.application.dto.FileReadDto;
import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FilePresenterFormatter implements FilePresenter {

  @Override
  public List<Response<FileCreateDto>> uploadFiles(List<File> files) {
    return files.stream()
        .map(file -> new Response<>(HttpStatus.CREATED, "파일을 성공적으로 업로드하였습니다.", new FileCreateDto(file.getDownloadUrl())))
        .collect(Collectors.toList());
  }

  @Override
  public Response<Pageable> readFiles(Page<FileReadDto> filePage) {
    return new Response(HttpStatus.OK, "파일을 성공적으로 조회하였습니다.", filePage);
  }

  @Override
  public ResponseEntity<Resource> downloadFile(FileDownloadDto fileDownloadDto) {
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(fileDownloadDto.getFileType()))
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDownloadDto.getOriginalName() + "\"")
        .body(fileDownloadDto.getResource());
  }

  @Override
  public Response<String> deleteFile() {
    return new Response<>(HttpStatus.OK, "파일 삭제를 완료했습니다.");
  }
}
