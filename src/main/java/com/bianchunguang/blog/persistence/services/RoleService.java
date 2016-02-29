package com.bianchunguang.blog.persistence.services;

import com.bianchunguang.blog.core.domain.Role;
import com.bianchunguang.blog.core.utils.Constants;

import java.util.UUID;

public interface RoleService extends AbstractService<Role, UUID> {

    Role findByCode(Constants.Role role);
}
