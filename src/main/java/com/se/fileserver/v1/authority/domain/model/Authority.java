package com.se.fileserver.v1.authority.domain.model;

import com.se.fileserver.v1.common.domain.model.AccountGenerateEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Authority extends AccountGenerateEntity implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long authorityId;

  @Column(length = 40, unique = true)
  @Size(min = 2, max = 40)
  private String nameEng;

  @Column(length = 40, unique = true)
  @Size(min = 2, max = 40)
  private String nameKor;

  @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<AuthorityGroupAuthorityMapping> authorityGroupAuthorityMapping = new ArrayList<>();

  public Authority(@Size(min = 2, max = 40) String nameEng, @Size(min = 2, max = 40) String nameKor) {
    this.nameEng = nameEng;
    this.nameKor = nameKor;
  }

  @Override
  public String getAuthority() {
    return nameEng;
  }

  public void updateNameEng(String nameEng) {
    this.nameEng = nameEng;
  }

  public void updateNameKor(String nameKor) {
    this.nameKor = nameKor;
  }

  public void addAuthorityGroupAuthorityMapping(AuthorityGroupAuthorityMapping authorityGroupAuthorityMapping){
    this.authorityGroupAuthorityMapping.add(authorityGroupAuthorityMapping);
  }
}