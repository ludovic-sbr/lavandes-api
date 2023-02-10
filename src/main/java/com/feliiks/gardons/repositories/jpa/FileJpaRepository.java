package com.feliiks.gardons.repositories.jpa;

import com.feliiks.gardons.models.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "rest")
public interface FileJpaRepository extends JpaRepository<FileModel, Long> {
}
