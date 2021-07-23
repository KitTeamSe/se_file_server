package com.se.fileserver.v1.user.infra.repository;

import com.se.fileserver.v1.user.domain.model.UserVO;
import com.se.fileserver.v1.user.domain.repository.UserRepositoryProtocol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserVO, String>, UserRepositoryProtocol {
  Optional<UserVO> findById(String user_id);
}
