package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.persistence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    private @Autowired UserService userService;

    @RequestMapping("")
    public List<User> getAllUser() {
        return userService.findAll();
    }
}
