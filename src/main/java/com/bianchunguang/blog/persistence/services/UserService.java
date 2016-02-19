package com.bianchunguang.blog.persistence.services;

import com.bianchunguang.blog.core.domain.User;

import java.util.UUID;

public interface UserService extends AbstractService<User, UUID> {

    User findByAccount(String account);
}
