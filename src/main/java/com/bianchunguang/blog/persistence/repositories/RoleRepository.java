package com.bianchunguang.blog.persistence.repositories;

import com.bianchunguang.blog.core.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByCode(String code);
}
