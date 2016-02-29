package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.persistence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class ControllerAdvices extends BaseController {

    private @Autowired UserService userService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public ResponseEntity<String> login() {
        return messageResponseEntity("会话超时，请重新登录", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/{template}")
    public String getTemplate(@PathVariable String template) {
        return template.replaceAll("-", "/");
    }

}
