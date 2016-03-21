package com.bianchunguang.blog.persistence.repositories.oauth2;

import com.bianchunguang.blog.core.domain.oauth2.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findById(String clientId);
}
