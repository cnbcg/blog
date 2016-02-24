package com.bianchunguang.blog.config.application;

import com.bianchunguang.blog.core.domain.Authority;
import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.core.utils.EmailSender;
import com.bianchunguang.blog.persistence.services.AuthorityService;
import com.bianchunguang.blog.persistence.services.BlogService;
import com.bianchunguang.blog.persistence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile({"develop"})
public class InitApplicationRunner implements ApplicationRunner {

    private @Autowired UserService userService;
    private @Autowired BlogService blogService;
    private @Autowired PasswordEncoder passwordEncoder;
    private @Autowired AuthorityService authorityService;
    private @Autowired EmailSender emailSender;

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
