package com.se.fileserver.v1.user.domain.repository;

import com.se.fileserver.v1.user.domain.model.UserVO;
import java.util.Optional;

public interface UserRepositoryProtocol {
  Optional<UserVO> findById(String user_id);
}
