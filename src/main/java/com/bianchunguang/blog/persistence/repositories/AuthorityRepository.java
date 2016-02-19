package com.bianchunguang.blog.persistence.repositories;

import com.bianchunguang.blog.core.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
}
