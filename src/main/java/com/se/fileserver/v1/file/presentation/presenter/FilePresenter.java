package com.se.fileserver.v1.file.presentation.presenter;

import com.se.fileserver.v1.file.presentation.response.Response;
import org.springframework.stereotype.Component;

public interface FilePresenter {
  Response<String> deleteFile();
}
