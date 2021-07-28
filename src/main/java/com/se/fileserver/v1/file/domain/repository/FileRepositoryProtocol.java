package com.se.fileserver.v1.file.domain.repository;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.Optional;

public interface FileRepositoryProtocol {
    Optional<File> findBySaveName(String fileName);
}
