package com.qlog.backend.post.domain.repository;

import com.qlog.backend.post.domain.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
