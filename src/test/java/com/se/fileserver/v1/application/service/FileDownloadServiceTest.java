package com.se.fileserver.v1.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.common.infra.security.config.WebSecurityConfig;
import com.se.fileserver.v1.common.infra.security.provider.JwtTokenResolver;
import com.se.fileserver.v1.file.adapter.controller.FileController;
import com.se.fileserver.v1.file.application.dto.FileDownloadDto;
import com.se.fileserver.v1.file.application.service.FileDownloadService;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = FileController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
public class FileDownloadServiceTest {

  private MockMvc mockMvc;

  @MockBean
  private FileDownloadService fileDownloadService;

  // Spring Security 관련
  @MockBean
  private JwtTokenResolver JwtTokenResolver;

  @Autowired
  private WebSecurityConfig webSecurityConfig;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Value("${se-file-server.upload-dir}")
  String directory;

  @Test
  void PNG_파일_다운로드_성공() throws Exception {
    // given
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String originalFileName = "PNG_FILE.png";
    String fileName = "teamlog.png";
    Path fileLocation = Paths.get(directory).toAbsolutePath().normalize();
    Path filePath = fileLocation.resolve("se").resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());
    String fileType = "image/png";

    FileDownloadDto fileDownloadDto = FileDownloadDto.builder()
        .originalName(originalFileName)
        .resource(resource)
        .fileType(fileType)
        .build();
    given(fileDownloadService.downloadFile(fileName)).willReturn(fileDownloadDto);

    // when
    final ResultActions actions = mockMvc.perform(
        get("/file-server/v1/" + fileName)
    );

    // then
    actions.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.IMAGE_PNG))
        .andDo(print());
  }

  @Test
  void JPEG_파일_다운로드_성공() throws Exception {
    // given
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String originalFileName = "JPG_FILE.JPG";
    String fileName = "cat.JPG";
    Path fileLocation = Paths.get(directory).toAbsolutePath().normalize();
    Path filePath = fileLocation.resolve("se").resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());
    String fileType = "image/jpeg";

    FileDownloadDto fileDownloadDto = FileDownloadDto.builder()
        .originalName(originalFileName)
        .resource(resource)
        .fileType(fileType)
        .build();
    given(fileDownloadService.downloadFile(fileName)).willReturn(fileDownloadDto);

    // when
    final ResultActions actions = mockMvc.perform(
        get("/file-server/v1/" + fileName)
    );

    // then
    actions.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.IMAGE_JPEG))
        .andDo(print());
  }

  @Test
  void WORD_다운로드_성공() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String originalFileName = "WORD_FILE.docx";
    String fileName = "intro.docx";
    Path fileLocation = Paths.get(directory).toAbsolutePath().normalize();
    Path filePath = fileLocation.resolve("se").resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());
    String fileType = "application/msword";

    FileDownloadDto fileDownloadDto = FileDownloadDto.builder()
        .originalName(originalFileName)
        .resource(resource)
        .fileType(fileType)
        .build();
    given(fileDownloadService.downloadFile(fileName)).willReturn(fileDownloadDto);

    // when
    final ResultActions actions = mockMvc.perform(
        get("/file-server/v1/" + fileName)
    );

    // then
    actions.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.parseMediaType("application/msword")))
        .andDo(print());
  }

  @Test
  void 한글_다운로드_성공() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String originalFileName = "HWP_FILE.hwp";
    String fileName = "intro.hwp";
    Path fileLocation = Paths.get(directory).toAbsolutePath().normalize();
    Path filePath = fileLocation.resolve("se").resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());
    String fileType = "application/hansofthwp";

    FileDownloadDto fileDownloadDto = FileDownloadDto.builder()
        .originalName(originalFileName)
        .resource(resource)
        .fileType(fileType)
        .build();
    given(fileDownloadService.downloadFile(fileName)).willReturn(fileDownloadDto);

    // when
    final ResultActions actions = mockMvc.perform(
        get("/file-server/v1/" + fileName)
    );

    // then
    actions.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.parseMediaType("application/hansofthwp")))
        .andDo(print());
  }

  @Test
  void MP4_다운로드_성공() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String originalFileName = "엠피포.mp4";
    String fileName = "v.mp4";
    Path fileLocation = Paths.get(directory).toAbsolutePath().normalize();
    Path filePath = fileLocation.resolve("se").resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());
    String fileType = "vedio/mp4";

    FileDownloadDto fileDownloadDto = FileDownloadDto.builder()
        .originalName(originalFileName)
        .resource(resource)
        .fileType(fileType)
        .build();
    given(fileDownloadService.downloadFile(fileName)).willReturn(fileDownloadDto);

    // when
    final ResultActions actions = mockMvc.perform(
        get("/file-server/v1/" + fileName)
    );

    // then
    actions.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.parseMediaType(fileType)))
        .andDo(print());
  }

  @Test
  void GIF_다운로드_성공() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String originalFileName = "GIF_FILE.gif";
    String fileName = "sofa.gif";
    Path fileLocation = Paths.get(directory).toAbsolutePath().normalize();
    Path filePath = fileLocation.resolve("se").resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());
    String fileType = "image/gif";

    FileDownloadDto fileDownloadDto = FileDownloadDto.builder()
        .originalName(originalFileName)
        .resource(resource)
        .fileType(fileType)
        .build();
    given(fileDownloadService.downloadFile(fileName)).willReturn(fileDownloadDto);

    // when
    final ResultActions actions = mockMvc.perform(
        get("/file-server/v1/" + fileName)
    );

    // then
    actions.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.IMAGE_GIF))
        .andDo(print());
  }

  @Test
  void 존재하지_않는_파일() throws Exception {
    // given
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .addFilters(new CharacterEncodingFilter("UTF-8", true)).alwaysDo(print()).build();
    String originalFileName = "파일.png";
    // DB의 save_name
    String fileName = "teamlog.png";
    // 존재하지 않는 파일의 이름
    String notExistentFile = "teamlog2.png";
    Path fileLocation = Paths.get(directory).toAbsolutePath().normalize();
    Path filePath = fileLocation.resolve("se").resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());
    String fileType = "image/png";

    FileDownloadDto fileDownloadDto = FileDownloadDto.builder()
        .originalName(originalFileName)
        .resource(resource)
        .fileType(fileType)
        .build();
    given(fileDownloadService.downloadFile(fileName)).willReturn(fileDownloadDto);

    final ResultActions actions = mockMvc.perform(
        get("/file-server/v1/" + notExistentFile)
    );

    // then
    actions.andExpect(status().isNotFound())
        .andExpect(content().string("{\"message\":\"파일이 존재하지 않습니다.\"}")).andDo(print());
  }

  @Test
  void 존재하지_않는_URL() throws Exception {
    String fileName = "teamlog.png";

    try {
      throw new MalformedURLException();
    } catch (MalformedURLException e) {
      // given
      e.printStackTrace();
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
          .addFilters(new CharacterEncodingFilter("UTF-8", true)).alwaysDo(print()).build();
      given(fileDownloadService.downloadFile(fileName))
          .willThrow(new NotFoundException(e.getMessage()));

      // when
      final ResultActions actions = mockMvc.perform(
          get("/file-server/v1/" + fileName)
      );

      // then
      actions.andExpect(status().isNotFound())
          .andExpect(content().string("{\"message\":" + e.getMessage() + "}"))
          .andDo(print());
    }
  }
}
