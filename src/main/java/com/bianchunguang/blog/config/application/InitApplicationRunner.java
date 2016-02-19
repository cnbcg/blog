package com.bianchunguang.blog.config.application;

import com.bianchunguang.blog.core.domain.Authority;
import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.User;
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

@Component
@Profile({"develop"})
public class InitApplicationRunner implements ApplicationRunner {

    private @Autowired UserService userService;
    private @Autowired BlogService blogService;
    private @Autowired PasswordEncoder passwordEncoder;
    private @Autowired AuthorityService authorityService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user = new User();
        user.setNickname("屌丝码农");
        user.setUsername("cnbcg");
        user.setEmail("bcg@bcg.com");
        user.setPassword(passwordEncoder.encode("bcg"));
        Authority authority = new Authority();
        authority.setAuthorityType(Authority.AuthorityType.ADMIN);
        user.setAuthorities(Arrays.asList(authority));

        User user2 = new User();
        user2.setNickname("屌丝码农2");
        user2.setUsername("cnbcg2");
        user2.setEmail("bcg@bcg.com");
        user2.setPassword(passwordEncoder.encode("bcg"));
        user2.setAuthorities(Arrays.asList(authority));

        userService.deleteAll();
        authorityService.deleteAll();
        authorityService.save(authority);
        userService.save(user);
        userService.save(user2);

        //blogService.deleteAll();
        for (int i = 0; i < 0; i++) {
            Blog blog = new Blog();
            blog.setAuthor(user);
            blog.setTitle("No." + i + " / " + go((int) (Math.random() * 26)));
            blog.setContent(go((int) (Math.random() * 3266)));
            blogService.save(blog);
        }
    }

    public static String go(int length) {

        String a = "";
        for (int i = 0; i < length; i++) {
            char c = '人';
            c = (char) (c + (int) (Math.random() * 12226));
            a += c;
        }

        return a;
    }
}
