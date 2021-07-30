package com.se.fileserver.v1.file.presentation;

import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.application.dto.FileCreateDto;
import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FilePresenterFormatter implements FilePresenter{

  @Override
  public Response<File> uploadFile(File file) {
    return new Response<>(HttpStatus.CREATED, "성공적으로 파일을 업로드하였습니다.", file);
  }

  @Override
  public List<Response<FileCreateDto>> uploadFiles(List<File> files) {
    return files.stream()
        .map(file -> new Response<>(HttpStatus.CREATED, "파일을 성공적으로 업로드하였습니다.", new FileCreateDto(file.getDownloadUrl())))
        .collect(Collectors.toList());
  }

}
