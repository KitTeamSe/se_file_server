package com.se.fileserver.v1.file.application.service;

import com.se.fileserver.v1.common.application.dto.request.PaginationRequest;
import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import com.se.fileserver.v1.file.application.dto.FileReadDto;
import com.se.fileserver.v1.file.application.dto.request.FileReadRequestDto;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FileReadService {

  private final FileRepositoryProtocol fileRepository;

  public FileReadService(FileRepositoryProtocol fileRepository) {
    this.fileRepository = fileRepository;
  }

  /* 목록조회할때, 서비스별로 보여주어야 하고, 서비스값이 없을 때는 모두 보여준다. 존재하지 않는 서비스값이면 예외 던진다. */
  public PageImpl readAll(PaginationRequest<FileReadRequestDto> request) {

    Page<File> filePage;

    if (request.getDto().getService() != null && !fileRepository.findFirstByService(request.getDto().getService()).isPresent())
      throw new NotFoundException("해당 서비스명과 일치하는 파일이 존재하지 않습니다.");

    if (request.getDto().getService() == null) {
      filePage = fileRepository.findAll(request.of());
    }

    else {
      filePage = fileRepository.findAllByService(request.of(), request.getDto().getService());
    }

    List<FileReadDto> fileList = filePage
        .getContent().stream()
        .map(file -> FileReadDto.to(file))
        .collect(Collectors.toList());

    return new PageImpl(fileList, filePage.getPageable(), filePage.getTotalElements());
  }

}
