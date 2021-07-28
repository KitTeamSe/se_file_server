package com.se.fileserver.v1.common.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PaginationRequest<T> extends BaseRequest<T> {

  @ApiModelProperty(example = "1", notes = "페이지, 1 이상만 가능")
  @Min(value = 1)
  private int page;
  @ApiModelProperty(example = "50", notes = "페이지의 사이즈, 10 이상 50 이하만 가능")
  @Min(value = 10)
  private int size;
  @ApiModelProperty(example = "ASC", notes = "정렬 방향")
  private Sort.Direction direction;
  @ApiModelProperty(example = "ASC", notes = "정렬 기준")
  private String orderBy;

}
