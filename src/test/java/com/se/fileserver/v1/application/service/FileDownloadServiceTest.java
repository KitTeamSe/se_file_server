package com.se.fileserver.v1.application.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.application.service.FileDownloadService;
import com.se.fileserver.v1.file.application.service.exception.InvalidFileException;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileDownloadServiceTest {

  private String directory = "./test";

  @Mock
  private FileRepositoryProtocol fileRepositoryProtocol;

  private FileDownloadService fileDownloadService;

  private void setUp(String saveName, String service, String originalName) throws IOException {
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
    Path fileLocation = Paths.get(directory).toAbsolutePath().normalize().resolve(service);

    Files.createDirectories(fileLocation);
    Files.copy(inputStream, fileLocation.resolve(saveName), StandardCopyOption.REPLACE_EXISTING);
  }

  private void close() {
    java.io.File testDirectory = new java.io.File(directory);
    java.io.File testService = Objects.requireNonNull(testDirectory.listFiles())[0];
    java.io.File testFile = null;

    if (Objects.requireNonNull(testService.listFiles()).length > 0) {
      testFile = Objects.requireNonNull(testService.listFiles())[0];
    }

    if (testFile != null) {
      testFile.delete();
    }

    if (testService.exists()) {
      testService.delete();
    }

    if (testDirectory.exists()) {
      testDirectory.delete();
    }
  }

  @Test
  void JPG_파일_다운로드_성공() throws IOException, InterruptedException {
    // given
    fileDownloadService = new FileDownloadService(directory, fileRepositoryProtocol);
    String saveName = "pepe.jpg";
    String service = "se";
    String originalName = "페페.jpg";
    setUp(saveName, service, originalName);
    File file = fileRepositoryProtocol.findBySaveName(saveName)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 파일입니다."));
    Path filePath = Paths.get(directory).resolve(file.getService()).resolve(saveName);
    Resource resource = new UrlResource(filePath.toUri());

    // when
    Thread.sleep(4000);
    FileDownloadDto fileDownloadDto = fileDownloadService.downloadFile(saveName);

    // then
    assertThat(fileDownloadDto.getFileType(), is("image/jpeg"));
    assertThat(fileDownloadDto.getOriginalName(),
        is(new String("페페.jpg".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
    assertThat(fileDownloadDto.getResource(),
        is(resource));
    close();
  }

  @Test
  void HWP_파일_다운로드_성공() throws IOException, InterruptedException {
    // given
    fileDownloadService = new FileDownloadService(directory, fileRepositoryProtocol);
    String service = "se";
    String originalFileName = "한글.hwp";
    String saveName = "han.hwp";
    setUp(saveName, service, originalFileName);
    File file = fileRepositoryProtocol.findBySaveName(saveName)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 파일입니다."));
    Path filePath = Paths.get(directory).resolve(file.getService()).resolve(saveName);
    Resource resource = new UrlResource(filePath.toUri());

    // when
    Thread.sleep(4000);
    FileDownloadDto fileDownloadDto = fileDownloadService.downloadFile(saveName);

    // then
    // assertThat(fileDownloadDto.getFileType(), is("application/haansofthwp"));
    assertThat(fileDownloadDto.getOriginalName(),
        is(new String("한글.hwp".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
    assertThat(fileDownloadDto.getResource(),
        is(resource));
    close();
  }

  @Test
  void 존재하지_않는_파일() throws IOException {
    // given
    String service = "se";
    String originalFileName = "한글.hwp";
    String saveName = "han.hwp";
    setUp(saveName, service, originalFileName);
    String notExistentSaveName = "pepe2.jpg";

    fileDownloadService = new FileDownloadService(directory, fileRepositoryProtocol);
    given(fileRepositoryProtocol.findBySaveName(notExistentSaveName))
        .willThrow(new NotFoundException("존재하지 않는 파일입니다."));

    // when
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
        () -> fileDownloadService.downloadFile(notExistentSaveName));
    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 파일입니다."));
    close();
  }

  @Test
  void 존재하지_않는_파일_경로() {
    // given
    String notExistentDirectory = "C:/Users/NotExistent";
    fileDownloadService = new FileDownloadService(notExistentDirectory, fileRepositoryProtocol);
    String saveName = "pepe2.png";

    // when
    NotFoundException notFoundException
        = assertThrows(NotFoundException.class, () -> fileDownloadService.downloadFile(saveName));

    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 파일 경로입니다."));
  }

  @Test
  void 존재하지_않는_리소스() throws IOException {
    // given
    fileDownloadService = new FileDownloadService("", fileRepositoryProtocol);
    String saveName = "pepe.jpg";
    String service = "se";
    String originalName = "페페.jpg";
    setUp(saveName, service, originalName);
    FileDownloadDto fileDownloadDto = null;

    // when
    NotFoundException notFoundException
        = assertThrows(NotFoundException.class, () -> fileDownloadService.downloadFile(saveName));
    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 리소스입니다."));

    assertThat(fileDownloadDto, is(nullValue()));
  }

  @Test
  void 파일_명에_사용할_수_없는_문자_포함() {
    // given
    fileDownloadService = new FileDownloadService("", fileRepositoryProtocol);
    String saveName = "..test.txt";

    // when
    InvalidFileException invalidFileException
        = assertThrows(InvalidFileException.class,
        () -> fileDownloadService.downloadFile(saveName));

    // then
    assertThat(invalidFileException.getMessage(), is("파일 명에 사용할 수 없는 문자가 포함되어 있습니다."));
  }
}