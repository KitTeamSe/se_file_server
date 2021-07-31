package com.se.fileserver.v1.application.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.file.application.service.FileDeleteService;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileDeleteServiceTest {

  private String directory = "C:\\Users\\Administrator\\Desktop\\attachment";

  @Mock
  private FileRepositoryProtocol fileRepositoryProtocol;

  private FileDeleteService fileDeleteService;

  @Test
  void 파일_삭제_성공() {
    fileDeleteService = new FileDeleteService(directory, fileRepositoryProtocol);
    String saveName = "file.png";
    String downloadUrl = "http://localhost:8070/file/download/file.png";
    String service = "se";
    String fileType = "image/png";
    String originalName = "팀로그.png";
    Long size = 1L;

    File file = new File(downloadUrl, service, fileType, originalName, saveName, size);
    given(fileRepositoryProtocol.findBySaveName(saveName)).willReturn(file);
    willDoNothing().given(fileRepositoryProtocol).delete(file);

    try {
      // when
      fileDeleteService.deleteFile(service, saveName);
    } catch (NotFoundException e) {
      e.printStackTrace();
    }
    // then
    System.out.println("삭제 완료");
  }

  @Test
  void 존재하지_않는_파일() {
    fileDeleteService = new FileDeleteService(directory, fileRepositoryProtocol);
    String saveName = "file2.png";
    String downloadUrl = "http://localhost:8070/file/download/file.png";
    String service = "se";
    String fileType = "image/png";
    String originalName = "팀로그.png";
    Long size = 1L;

    File file = new File(downloadUrl, service, fileType, originalName, saveName, size);
    given(fileRepositoryProtocol.findBySaveName(saveName)).willReturn(file);
    willDoNothing().given(fileRepositoryProtocol).delete(file);

    try {
      // when
      fileDeleteService.deleteFile(service, saveName);
    } catch (NotFoundException e) {
      // then
      assertThat(e.getMessage(), is("존재하지 않는 파일입니다."));
    }
  }

  @Test
  void 존재하지_않는_파일_경로() {
    String notExistentPath = "C:\\Users\\NotExistent";
    String saveName = "file2.png";
    String service = "se";
    try {
      // when
      fileDeleteService = new FileDeleteService(notExistentPath, fileRepositoryProtocol);
      fileDeleteService.deleteFile(service, saveName);
    } catch (NotFoundException e) {
      // then
      assertThat(e.getMessage(), is("존재하지 않는 파일 경로입니다."));
    }
  }

  @Test
  void 파일_삭제_실패() {
    fileDeleteService = new FileDeleteService(directory, fileRepositoryProtocol);
    String saveName = "d447.mp4";
    String downloadUrl = "http://localhost:8070/file/download/d447.png";
    String service = "se";
    String fileType = "image/png";
    String originalName = "d447.mp4";
    Long size = 1L;

    File file = new File(downloadUrl, service, fileType, originalName, saveName, size);
    given(fileRepositoryProtocol.findBySaveName(saveName)).willReturn(file);
    willDoNothing().given(fileRepositoryProtocol).delete(file);

    try {
      // when
      fileDeleteService.deleteFile(service, saveName);
    } catch (NotFoundException e) {
      // then
      assertThat(e.getMessage(), is("존재하지 않는 파일입니다."));
    }
  }
}
