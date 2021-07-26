package com.se.fileserver.v1.common.application.dto;

import io.swagger.annotations.ApiModelProperty;

public class CommonRequest <T> {
  @ApiModelProperty
  private T dto;

  public CommonRequest() {
  }

  public CommonRequest(T dto) {
    this.dto = dto;
  }

  public T getDto() {
    return dto;
  }
}
