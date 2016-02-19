package com.bianchunguang.blog.persistence.services.impl;

import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.persistence.repositories.UserRepository;
import com.bianchunguang.blog.persistence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, UUID> implements UserService {

    private @Autowired UserRepository userRepository;

    @Override
    protected JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public User findByAccount(String account) {
        return userRepository.findByUsername(account);
    }
}
