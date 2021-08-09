package com.se.fileserver.v1.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.se.fileserver.v1.common.application.dto.request.PaginationRequest;
import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.file.application.dto.request.FileReadRequestDto;
import com.se.fileserver.v1.file.application.service.FileReadService;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort.Direction;

@ExtendWith(MockitoExtension.class)
public class FileReadServiceTest {

  @Mock
  private FileRepositoryProtocol fileRepository;

  @InjectMocks
  private FileReadService fileReadService;

  @BeforeAll
  static void setUp() {
  }

  @Test
  public void 서비스값_없음_성공() throws Exception{
    //given
    PaginationRequest<FileReadRequestDto> request = new PaginationRequest(new FileReadRequestDto(null),0,10,Direction.ASC, "createdAt");
    Page<File> filePage = new PageImpl(Collections.emptyList(), request.of(), 0L);
    when(fileRepository.findAll(request.of())).thenReturn(filePage);
    //when
    //then
    assertDoesNotThrow(() -> fileReadService.readAll(request));
  }

  @Test
  public void 서비스값_존재_성공() throws Exception{
    //given
    PaginationRequest<FileReadRequestDto> request = new PaginationRequest(new FileReadRequestDto("se"),0,10,Direction.ASC, "createdAt");
    Page<File> filePage = new PageImpl(Collections.emptyList(), request.of(), 0L);

    File file = mock(File.class);
    when(fileRepository.findFirstByService(anyString())).thenReturn(Optional.ofNullable(file));
    when(fileRepository.findAllByService(request.of(), request.getDto().getService())).thenReturn(filePage);
    //when
    //then
    assertDoesNotThrow(() -> fileReadService.readAll(request));
  }
  @Test
  public void 서비스값_존재하지않음_실패() throws Exception{
    //given
    PaginationRequest<FileReadRequestDto> request = new PaginationRequest(
        new FileReadRequestDto("se"),0,10,Direction.ASC, "createdAt");
    when(fileRepository.findFirstByService(anyString())).thenReturn(Optional.ofNullable(null));

    //when
    NotFoundException exception = assertThrows(NotFoundException.class, () -> fileReadService.readAll(request));
    //then
    assertEquals("해당 서비스명과 일치하는 파일이 존재하지 않습니다.", exception.getMessage());
  }
}
