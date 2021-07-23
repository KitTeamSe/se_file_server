package com.se.fileserver.v1.Account.infra.repository;

import com.se.fileserver.v1.Account.domain.model.Account;
import com.se.fileserver.v1.Account.domain.repository.AccountRepositoryProtocol;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<Account, String>,
    AccountRepositoryProtocol {
  Optional<Account> findById(String user_id);
  List<Account> findAll();
}
