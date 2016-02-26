package com.bianchunguang.blog.persistence.services.impl;

import com.bianchunguang.blog.core.domain.SystemConfig;
import com.bianchunguang.blog.persistence.repositories.SystemConfigRepository;
import com.bianchunguang.blog.persistence.services.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class SystemConfigServiceImpl extends AbstractServiceImpl<SystemConfig, UUID> implements SystemConfigService {

    private @Autowired SystemConfigRepository systemConfigRepository;

    @Override
    protected JpaRepository<SystemConfig, UUID> getRepository() {
        return systemConfigRepository;
    }

    @Override
    public boolean exist(String name) {
        return systemConfigRepository.findByName(name) != null;
    }

    @Override
    public SystemConfig findByName(String name) {
        return systemConfigRepository.findByName(name);
    }
}
