package com.se.fileserver.v1.authority.domain.repository;

import com.se.fileserver.v1.authority.domain.model.AuthorityGroup;
import java.util.Optional;

public interface AuthorityGroupRepositoryProtocol {
  Optional<AuthorityGroup> findByAccountIdString(String idString);
}
