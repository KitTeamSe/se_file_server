package com.se.fileserver.v1.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.se.fileserver.v1.common.domain.exception.AttachmentTooLargeException;
import com.se.fileserver.v1.file.application.service.FileUploadService;
import com.se.fileserver.v1.file.application.service.exception.InvalidFileException;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class FileUploadServiceTest {

  @MockBean
  private FileRepositoryProtocol fileRepository;

  @Autowired
  private FileUploadService fileUploadService;

  private static final String TEST_DIR = "./testDir";

  private String createByteString(Long size) {
    String str = "";
    for (Long i=0L; i<size; i++) str += "a";
    return str;
  }

  private MockMultipartFile createMockMultipartFile(String fileName, Long size) {
    byte[] content = createByteString(size).getBytes();
    return new MockMultipartFile(fileName, fileName+".txt", "text/plain", content);
  }

  void closeIOFileResource(String location) throws IOException {
    java.io.File targetDir = new java.io.File(location);
    FileUtils.deleteDirectory(targetDir);
  }

  @Test
  public void 단일_파일_업로드_성공() throws Exception{
    //given
    ReflectionTestUtils.setField(fileUploadService,"uploadDir",Paths.get(TEST_DIR));

    File mockFile = mock(File.class);
    MockMultipartFile mockMultipartFile = createMockMultipartFile("fileName", 1L);
    String service = "service";

    when(fileRepository.save(mockFile)).thenReturn(mockFile);
    when(fileRepository.findBySaveName(anyString())).thenReturn(Optional.ofNullable(null));

    //when
    //then
    assertDoesNotThrow(() -> fileUploadService.uploadOne(mockMultipartFile, service));

    closeIOFileResource(TEST_DIR);
  }

  @Test
  public void 다중_파일_업로드_성공() throws Exception{
    //given
    ReflectionTestUtils.setField(fileUploadService,"uploadDir",Paths.get(TEST_DIR));

    List<File> mockFileList = mock(List.class);
    List<MultipartFile> mockMultipartFileList = new ArrayList<>();
    MockMultipartFile mockMultipartFile1 = createMockMultipartFile("fileName1", 1L);
    MockMultipartFile mockMultipartFile2 = createMockMultipartFile("fileName2", 1L);
    mockMultipartFileList.add(mockMultipartFile1);
    mockMultipartFileList.add(mockMultipartFile2);

    String service = "service";

    when(fileRepository.saveAll(mockFileList)).thenReturn(mockFileList);
    when(fileRepository.findBySaveName(anyString())).thenReturn(Optional.ofNullable(null));

    //when
    //then
    assertDoesNotThrow(() -> fileUploadService.upload(mockMultipartFileList, service));

    closeIOFileResource(TEST_DIR);
  }

  @Test
  public void 파일_null_실패() throws Exception{
    // given
    MockMultipartFile mockMultipartFile = null;

    ReflectionTestUtils.setField(fileUploadService,"uploadDir",Paths.get(TEST_DIR));
    String service = "service";

    // when
    InvalidFileException exception = assertThrows(InvalidFileException.class, () -> fileUploadService.uploadOne(mockMultipartFile, service));

    // then
    assertEquals("파일을 가져오지 못하였습니다.", exception.getMessage());
  }

  @Test
  public void 파일_이름_부적합_실패 () throws Exception{
    //given
    String invalidFileName = "..";

    ReflectionTestUtils.setField(fileUploadService,"uploadDir",Paths.get(TEST_DIR));
    MockMultipartFile mockMultipartFile = createMockMultipartFile(invalidFileName,1L);
    String service = "service";

    //when
    InvalidFileException exception = assertThrows(InvalidFileException.class, () -> fileUploadService.uploadOne(mockMultipartFile, service));

    //then
    assertEquals("파일명에 [..]가 존재합니다 : " + mockMultipartFile.getOriginalFilename(), exception.getMessage());
  }

  @Test
  public void 파일_최소크기_미만_실패() throws Exception{
    //given
    Long invalidFileSize = 0L;

    ReflectionTestUtils.setField(fileUploadService,"uploadDir",Paths.get(TEST_DIR));
    MockMultipartFile mockMultipartFile = createMockMultipartFile("fileName",invalidFileSize);
    String service = "service";

    //when
    InvalidFileException exception = assertThrows(InvalidFileException.class, () -> fileUploadService.uploadOne(mockMultipartFile, service));

    //then
    assertEquals("파일 크기가 0보다 작습니다 : " + mockMultipartFile.getOriginalFilename(), exception.getMessage());
  }

  @Test
  public void 파일_최대크기_초과_실패() throws Exception{
    Long invalidFileSize = 101L;
    Long maxFileSize = 100L;
    ReflectionTestUtils.setField(fileUploadService,"uploadDir",Paths.get(TEST_DIR));
    ReflectionTestUtils.setField(fileUploadService, "maxFileSize", maxFileSize);
    MockMultipartFile mockMultipartFile = createMockMultipartFile("fileName",invalidFileSize);
    String service = "service";

    //when
    AttachmentTooLargeException exception = assertThrows(AttachmentTooLargeException.class, () -> fileUploadService.uploadOne(mockMultipartFile, service));

    //then
    assertEquals("파일 크기가 " + maxFileSize + "를 초과합니다 : " + mockMultipartFile.getOriginalFilename(), exception.getMessage());
  }
}
