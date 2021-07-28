package com.se.fileserver.v1.common.application.dto.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class BaseRequest<T> {
  @JsonUnwrapped
  @ApiModelProperty
  private T dto;

  public BaseRequest(T dto) {
    this.dto = dto;
  }
}
