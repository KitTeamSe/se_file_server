package com.se.fileserver.v1.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.se.fileserver.v1.common.domain.exception.BusinessException;
import com.se.fileserver.v1.common.infra.security.config.WebSecurityConfig;
import com.se.fileserver.v1.common.infra.security.filter.JwtAuthenticationFilters;
import com.se.fileserver.v1.common.infra.security.provider.JwtTokenResolver;
import com.se.fileserver.v1.config.FileProperties;
import com.se.fileserver.v1.file.adapter.controller.FileController;
import com.se.fileserver.v1.file.application.dto.FileDownloadVo;
import com.se.fileserver.v1.file.application.service.FileDownloadService;
import com.se.fileserver.v1.file.application.service.error.FileErrorCode;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertThrows;


@WebMvcTest(value = FileController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
@EnableConfigurationProperties({FileProperties.class, })
public class FileDownloadServiceTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FileDownloadService fileDownloadService;

  // 파일 관련
  @Autowired
  FileProperties prop;

  // Spring Security 관련
  @MockBean
  private JwtTokenResolver JwtTokenResolver;

  @Autowired
  private WebSecurityConfig webSecurityConfig;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Test
  void PNG_파일_다운로드_성공() throws Exception {
    // given
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String originalFileName = "파일.png";
    String fileName = "teamlog.png";
    Path fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
    Path filePath = fileLocation.resolve("se").resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());
    String fileType = "image/png";

    FileDownloadVo fileDownloadVo = new FileDownloadVo(originalFileName, resource, fileType);
    given(fileDownloadService.downloadFile(fileName)).willReturn(fileDownloadVo);

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
  void 존재하지_않는_파일() throws Exception {
    // given
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String originalFileName = "파일.png";
    // DB의 save_name
    String fileName = "teamlog.png";
    // 존재하지 않는 파일의 이름
    String notExistentFile = "teamlog2.png";
    Path fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
    Path filePath = fileLocation.resolve("se").resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());
    String fileType = "image/png";

    FileDownloadVo fileDownloadVo = new FileDownloadVo(originalFileName, resource, fileType);
    given(fileDownloadService.downloadFile(fileName)).willReturn(fileDownloadVo);

    // when
    BusinessException businessException = assertThrows(BusinessException.class, () -> mockMvc.perform(
        get("/file-server/v1/" + notExistentFile)
    ));

    // then
    assertEquals(businessException.getErrorCode().getMessage(), FileErrorCode.FILE_DOES_NOT_EXISTS);
  }
}
