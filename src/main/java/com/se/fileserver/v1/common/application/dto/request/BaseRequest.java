package com.se.fileserver.v1.common.application.dto.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;

public class BaseRequest<T> {
  @JsonUnwrapped
  @ApiModelProperty
  private T dto;

  public BaseRequest() {
  }

  public BaseRequest(T dto) {
    this.dto = dto;
  }

  public T getDto() {
    return dto;
  }
}
