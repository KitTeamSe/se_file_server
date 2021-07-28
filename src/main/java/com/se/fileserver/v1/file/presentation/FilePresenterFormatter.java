package com.se.fileserver.v1.file.presentation;

import com.se.fileserver.v1.common.presentation.response.Response;
import com.se.fileserver.v1.file.domain.model.File;
import java.util.List;
import lombok.Builder.ObtainVia;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FilePresenterFormatter implements FilePresenter{

  @Override
  public Response<File> uploadFile(File file) {
    return new Response<>(HttpStatus.CREATED, "성공적으로 파일을 업로드하였습니다.", file);
  }

  @Override
  public Response<List<File>> uploadFiles(List<File> files) {
    return new Response<>(HttpStatus.CREATED, "성공적으로 여러 개의 파일을 업로드하였습니다.", files);
  }

}
