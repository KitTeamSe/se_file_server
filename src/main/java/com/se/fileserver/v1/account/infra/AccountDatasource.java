package com.se.fileserver.v1.account.infra;

import com.se.fileserver.v1.account.domain.model.AccountType;
import com.se.fileserver.v1.account.domain.model.InformationOpenAgree;
import com.se.fileserver.v1.account.domain.model.Question;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class AccountDatasource {
  @Value("${spring.datasource.admin.id}")
  private String id;
  @Value("${spring.datasource.admin.password}")
  private String password;
  @Value("${spring.datasource.admin.name}")
  private String name;
  @Value("${spring.datasource.admin.nickname}")
  private String nickname;
  @Value("${spring.datasource.admin.studentId}")
  private String studentId;
  private AccountType type = AccountType.ASSISTANT;
  @Value("${spring.datasource.admin.email}")
  private String email;
  private InformationOpenAgree informationOpenAgree = InformationOpenAgree.AGREE;
  private Question question = new Question(0L,"none");
  private String answer = "none";
}
