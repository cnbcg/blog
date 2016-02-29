package com.bianchunguang.blog.persistence.repositories;

import com.bianchunguang.blog.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String phone);

    User findByActivateCode(UUID activateCode);

}
