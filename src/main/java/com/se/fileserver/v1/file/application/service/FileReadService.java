package com.se.fileserver.v1.file.application.service;

import com.se.fileserver.v1.common.application.dto.request.PaginationRequest;
import com.se.fileserver.v1.file.application.dto.FileReadDto;
import com.se.fileserver.v1.file.application.dto.request.FileReadRequestDto;
import com.se.fileserver.v1.file.domain.model.File;
import com.se.fileserver.v1.file.domain.repository.FileRepository;
import com.se.fileserver.v1.file.domain.repository.FileRepositoryProtocol;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FileReadService {

  private final FileRepositoryProtocol fileRepository;

  public FileReadService(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  /* 목록조회할때, 서비스별로 보여줘야한다 */
  public PageImpl readAll(PaginationRequest<FileReadRequestDto> request) {

    Page<File> filePage;

    if (request.getDto().getService() == null) {
      filePage = fileRepository.findAll(request.of());
    }

    else {
      filePage = fileRepository.findAllByService(request.of(), request.getDto().getService());
    }
    System.out.println(filePage.getTotalElements());
    System.out.println(filePage.getContent().size());

    List<FileReadDto> fileList = filePage
        .getContent().stream()
        .map(file -> FileReadDto.to(file))
        .collect(Collectors.toList());

    //List<File> fileList = filePage.getContent();

    System.out.println(fileList.size());
    return new PageImpl(fileList, filePage.getPageable(), filePage.getTotalElements());
  }

  public File read(Long fileId) {
    return fileRepository.findByFileId(fileId);
  }

}
