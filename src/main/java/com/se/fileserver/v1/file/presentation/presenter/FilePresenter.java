package com.se.fileserver.v1.file.presentation.presenter;

import com.se.fileserver.v1.file.presentation.response.Response;

public interface FilePresenter {
  Response<String> deleteFile();
}
