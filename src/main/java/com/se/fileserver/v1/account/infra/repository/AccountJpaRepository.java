package com.se.fileserver.v1.account.infra.repository;

import com.se.fileserver.v1.account.domain.model.Account;
import com.se.fileserver.v1.account.domain.repository.AccountRepositoryProtocol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<Account, Long>,
    AccountRepositoryProtocol {

}
