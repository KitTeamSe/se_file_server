package com.se.fileserver.v1.account.domain.model;

import com.se.fileserver.v1.common.domain.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long questionId;

  @Column(length = 100)
  @Size(min = 2, max = 100)
  private String text;

  public Question(Long questionId, @Size(min = 2, max = 100) String text) {
    this.questionId = questionId;
    this.text = text;
  }
}
