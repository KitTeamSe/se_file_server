package com.se.fileserver.v1.application.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.file.application.service.FileDeleteService;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

  void setUp(String saveName, String service, String originalName) throws IOException {
    String sourceLocation =
        "src/test/java/com/se/fileserver/v1/application/service/file/" + originalName;
    String downloadUrl = "http://localhost:8070/file-server/v1/file/" + saveName;
    String fileType = Files
        .probeContentType(Paths.get(sourceLocation).toAbsolutePath().normalize());
    Long size = 1L;

    File file = new File(downloadUrl, service, fileType, originalName, saveName, size);
    given(fileRepositoryProtocol.save(file)).willReturn(file);
    given(fileRepositoryProtocol.findBySaveName(saveName)).willReturn(java.util.Optional.of(file));

    FileInputStream inputStream = new FileInputStream(sourceLocation);
    Path fileLocation = Paths.get(this.directory).toAbsolutePath().normalize().resolve(service);

    Files.createDirectories(fileLocation);
    Files.copy(inputStream, fileLocation.resolve(saveName), StandardCopyOption.REPLACE_EXISTING);
  }

  @Test
  void 파일_삭제_성공() throws IOException, InterruptedException {
    // given
    fileDeleteService = new FileDeleteService(directory, fileRepositoryProtocol);
    String saveName = "pepe.jpg";
    String service = "se";
    String originalName = "페페.jpg";
    setUp(saveName, service, originalName);
    Thread.sleep(4000);

    // when
    fileDeleteService.deleteFile(service, saveName);
  }

  @Test
  void 존재하지_않는_파일() throws IOException, InterruptedException {
    // given
    fileDeleteService = new FileDeleteService(directory, fileRepositoryProtocol);
    String nonExistentSaveName = "pepe2.png";
    String saveName = "pepe.png";
    String service = "se";
    String originalName = "페페.jpg";
    setUp(saveName, service, originalName);
    Thread.sleep(4000);

    // when
    try {
      fileDeleteService.deleteFile(service, nonExistentSaveName);
    } catch (NotFoundException e) {
      // then
      assertThat(e.getMessage(), is("존재하지 않는 파일입니다."));
    }
  }

  @Test
  void 존재하지_않는_파일_경로() {
    String notExistentDirectory = "C:/Users/NotExistent";
    fileDeleteService = new FileDeleteService(notExistentDirectory, fileRepositoryProtocol);
    String saveName = "file2.png";
    String service = "se";

    try {
      // when
      fileDeleteService.deleteFile(service, saveName);
    } catch (NotFoundException e) {
      // then
      assertThat(e.getMessage(), is("존재하지 않는 파일 경로입니다."));
    }
  }
}