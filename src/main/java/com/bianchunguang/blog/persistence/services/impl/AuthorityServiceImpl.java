package com.bianchunguang.blog.persistence.services.impl;

import com.bianchunguang.blog.core.domain.Authority;
import com.bianchunguang.blog.persistence.repositories.AuthorityRepository;
import com.bianchunguang.blog.persistence.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorityServiceImpl extends AbstractServiceImpl<Authority, UUID> implements AuthorityService {

    private @Autowired AuthorityRepository authorityRepository;

    @Override
    protected JpaRepository<Authority, UUID> getRepository() {
        return authorityRepository;
    }
}
