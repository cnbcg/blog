package com.bianchunguang.blog.config.application;

import com.bianchunguang.blog.core.domain.Role;
import com.bianchunguang.blog.core.utils.Constants;
import com.bianchunguang.blog.persistence.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InitApplicationRunner implements ApplicationRunner {

    private @Autowired RoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleService.count() == 0) {
            Role roleAdmin = new Role();
            roleAdmin.setCode(Constants.Role.ADMIN.toString());
            roleAdmin.setName("管理员");
            Role roleUser = new Role();
            roleUser.setCode(Constants.Role.USER.toString());
            roleUser.setName("注册用户");
            roleService.save(Arrays.asList(roleAdmin, roleUser));
        }
    }
}