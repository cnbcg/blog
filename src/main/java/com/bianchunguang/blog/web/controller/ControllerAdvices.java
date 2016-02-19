package com.bianchunguang.blog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@Controller
public class ControllerAdvices extends BaseController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value="/{template}")
    public String getTemplate(@PathVariable String template) {
        return template.replaceAll("-", "/");
    }

}
