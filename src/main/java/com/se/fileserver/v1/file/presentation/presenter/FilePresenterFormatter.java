package com.se.fileserver.v1.file.presentation.presenter;

import com.se.fileserver.v1.file.presentation.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FilePresenterFormatter implements FilePresenter {

  @Override
  public Response<String> deleteFile() {
    return new Response<>(HttpStatus.OK, "파일 삭제를 완료했습니다.");
  }
}
