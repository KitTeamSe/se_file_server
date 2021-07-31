package com.se.fileserver.v1.account.domain.repository;

import com.se.fileserver.v1.account.domain.model.Account;
import java.util.Optional;

public interface AccountRepositoryProtocol {
  Optional<Account> findByIdString(String id);
}
