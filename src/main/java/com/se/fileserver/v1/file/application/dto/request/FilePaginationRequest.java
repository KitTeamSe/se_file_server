package com.se.fileserver.v1.file.application.dto.request;

import com.se.fileserver.v1.common.application.dto.request.PaginationRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort.Direction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilePaginationRequest extends PaginationRequest {

  public FilePaginationRequest(Object dto, int page, int size,
      Direction direction, String orderBy) {
    super(dto, page, size, direction, orderBy);
  }
}
