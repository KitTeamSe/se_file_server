package com.se.fileserver.v1.Account.domain.repository;

import com.se.fileserver.v1.Account.domain.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepositoryProtocol {
  Optional<Account> findById(String user_id);
  List<Account> findAll();
}
