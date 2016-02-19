package com.bianchunguang.blog.persistence.repositories;

import com.bianchunguang.blog.core.domain.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, UUID> {

    SystemConfig findByName(String name);
}
