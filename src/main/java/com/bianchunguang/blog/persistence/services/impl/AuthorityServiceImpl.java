package com.bianchunguang.blog.persistence.services.impl;

import com.bianchunguang.blog.core.domain.Authority;
import com.bianchunguang.blog.persistence.repositories.AuthorityRepository;
import com.bianchunguang.blog.persistence.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AuthorityServiceImpl extends AbstractServiceImpl<Authority, UUID> implements AuthorityService {

    private @Autowired AuthorityRepository authorityRepository;

    @Override
    protected JpaRepository<Authority, UUID> getRepository() {
        return authorityRepository;
    }


    public Authority findByAuthorityType(Authority.AuthorityType authorityType) {
        return authorityRepository.findByAuthorityType(authorityType);
    }
}
