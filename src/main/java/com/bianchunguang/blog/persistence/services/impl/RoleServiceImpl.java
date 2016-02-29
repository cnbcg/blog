package com.bianchunguang.blog.persistence.services.impl;

import com.bianchunguang.blog.core.domain.Role;
import com.bianchunguang.blog.core.utils.Constants;
import com.bianchunguang.blog.persistence.repositories.RoleRepository;
import com.bianchunguang.blog.persistence.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class RoleServiceImpl extends AbstractServiceImpl<Role, UUID> implements RoleService {

    private @Autowired RoleRepository roleRepository;

    @Override
    protected JpaRepository<Role, UUID> getRepository() {
        return roleRepository;
    }

    public Role findByCode(Constants.Role role) {
        return roleRepository.findByCode(role.toString());
    }
}
