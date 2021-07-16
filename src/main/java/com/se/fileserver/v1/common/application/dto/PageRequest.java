package com.se.fileserver.v1.common.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@ApiModel("페이지 요청")
public class PageRequest {

  @ApiModelProperty(example = "1", notes = "페이지, 1 이상만 가능")
  @Min(value = 1)
  private int page;
  @ApiModelProperty(example = "50", notes = "페이지의 사이즈, 10 이상 50 이하만 가능")
  @Min(value = 10)
  private int size;

  @ApiModelProperty(example = "ASC", notes = "정렬 방향, 기준(생성일)")
  private Sort.Direction direction;

  public PageRequest() {
  }

  public PageRequest(@Min(value = 1) int page, @Min(value = 10) int size,
      Direction direction) {
    this.page = page;
    this.size = size;
    this.direction = direction;
  }

  public void setPage(int page) {
    this.page = page <= 0 ? 1 : page;
  }

  public void setSize(int size) {
    int DEFAULT_SIZE = 50;
    int MAX_SIZE = 50;
    this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
  }

  public void setDirection(Sort.Direction direction) {
    this.direction = direction;
  }

  public org.springframework.data.domain.PageRequest of() {
    return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, "createdAt");
  }
}
