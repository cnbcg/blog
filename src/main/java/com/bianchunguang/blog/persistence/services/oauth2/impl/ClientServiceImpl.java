package com.bianchunguang.blog.persistence.services.oauth2.impl;

import com.bianchunguang.blog.core.domain.oauth2.Client;
import com.bianchunguang.blog.persistence.repositories.oauth2.ClientRepository;
import com.bianchunguang.blog.persistence.services.impl.AbstractServiceImpl;
import com.bianchunguang.blog.persistence.services.oauth2.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ClientServiceImpl extends AbstractServiceImpl<Client, UUID> implements ClientService {

    private @Autowired ClientRepository clientRepository;

    @Override
    protected JpaRepository<Client, UUID> getRepository() {
        return clientRepository;
    }
}
