package com.se.fileserver.v1.application.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.se.fileserver.v1.common.infra.security.config.WebSecurityConfig;
import com.se.fileserver.v1.common.infra.security.provider.JwtTokenResolver;
import com.se.fileserver.v1.config.FileProperties;
import com.se.fileserver.v1.file.adapter.controller.FileController;
import com.se.fileserver.v1.file.application.service.FileDeleteService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(value = FileController.class)
@EnableConfigurationProperties({FileProperties.class, })
public class FileDeleteServiceTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FileDeleteService fileDeleteService;

  // 파일 관련련
 @Autowired
  private FileProperties prop;

  // Spring Security 관련
  @MockBean
  private JwtTokenResolver jwtTokenResolver;

  @Autowired
  private WebSecurityConfig webSecurityConfig;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Test
  void 파일_삭제_성공() throws Exception {
    // given
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String saveName = "teamlog.png";
    String service = "se";
    BDDMockito.given(fileDeleteService.deleteFile(saveName, service)).willReturn("Deleted successfully.");
    MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
    info.add("saveName", saveName);
    info.add("service", service);

    // when
    final ResultActions actions = mockMvc.perform(delete("/file-server/v1/" + saveName).params(info));
    // then
    actions.andExpect(status().isOk()).andExpect(content().string("Deleted successfully.")).andDo(print());
  }
}
