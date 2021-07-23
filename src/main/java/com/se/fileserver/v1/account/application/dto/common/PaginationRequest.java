package com.se.fileserver.v1.account.application.dto.common;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PaginationRequest<T> extends CommonRequest<T>{
  @ApiModelProperty(example = "1", notes = "페이지, 1 이상만 가능")
  @Min(value = 1)
  private int page;
  @ApiModelProperty(example = "50", notes = "페이지의 사이즈, 10 이상 50 이하만 가능")
  @Min(value = 10)
  private int size;
  @ApiModelProperty(example = "ASC", notes = "정렬 방향, 기준(생성일)")
  private Sort.Direction direction;

  public PaginationRequest() {
  }

  public PaginationRequest(T dto, int page, int size, Direction direction) {
    super(dto);
    this.page = page;
    this.size = size;
    this.direction = direction;
  }

  public int getPage() {
    return page;
  }

  public int getSize() {
    return size;
  }

  public Direction getDirection() {
    return direction;
  }
}
