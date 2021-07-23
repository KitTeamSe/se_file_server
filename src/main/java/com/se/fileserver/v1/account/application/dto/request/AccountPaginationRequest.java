package com.se.fileserver.v1.account.application.dto.request;

import com.se.fileserver.v1.account.application.dto.common.PaginationRequest;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Sort.Direction;

public class AccountPaginationRequest<T> extends PaginationRequest<T> {
  /*
  additional field
  * */
  public AccountPaginationRequest() {}

  public AccountPaginationRequest(T dto, int page, int size, Direction direction) {
    super(dto, page, size, direction);
  }
}
