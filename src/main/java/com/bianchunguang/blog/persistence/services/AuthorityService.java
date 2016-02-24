package com.bianchunguang.blog.persistence.services;

import com.bianchunguang.blog.core.domain.Authority;

import java.util.UUID;

public interface AuthorityService extends AbstractService<Authority, UUID> {

    public Authority findByAuthorityType(Authority.AuthorityType authorityType);
}
