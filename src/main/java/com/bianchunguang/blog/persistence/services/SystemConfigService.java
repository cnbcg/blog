package com.bianchunguang.blog.persistence.services;

import com.bianchunguang.blog.core.domain.SystemConfig;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface SystemConfigService extends AbstractService<SystemConfig, UUID> {

    boolean exist(String name);

    SystemConfig findByName(String name);
}
