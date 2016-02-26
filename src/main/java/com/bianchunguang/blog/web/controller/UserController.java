package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.core.domain.Authority;
import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.core.utils.EmailSender;
import com.bianchunguang.blog.core.utils.StringUtils;
import com.bianchunguang.blog.core.utils.UUIDGenerator;
import com.bianchunguang.blog.persistence.services.AuthorityService;
import com.bianchunguang.blog.persistence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    private @Autowired UserService userService;
    private @Autowired AuthorityService authorityService;
    private @Autowired EmailSender emailSender;
    private @Autowired PasswordEncoder passwordEncoder;

    @RequestMapping("")
    public List<User> getAllUser() {
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody @Valid User user, BindingResult result, HttpServletRequest request) {

        if (userService.findByAccount(user.getEmail()) != null) {
            return messageResponseEntity("邮箱已存在", HttpStatus.BAD_REQUEST);
        }

        if (userService.findByAccount(user.getUsername()) != null) {
            return messageResponseEntity("用户名已存在", HttpStatus.BAD_REQUEST);
        }

        if (userService.findByAccount(user.getUsername()) != null) {
            return messageResponseEntity("用户名已存在", HttpStatus.BAD_REQUEST);
        }

        if (user.getPassword() == null || !user.getPassword().matches("^.*(?=.*\\d)(?=.*[a-zA-Z]).*")) {
            return messageResponseEntity("密码需同时包含字母和数字，长度6到16位", HttpStatus.BAD_REQUEST);
        }

        if (result.getErrorCount() > 0) {
            return messageResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        user.setActivateCode(UUIDGenerator.generateUUID());
        user.setAuthorities(Arrays.asList(authorityService.findByAuthorityType(Authority.AuthorityType.USER)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        Map<String, Object> mailContentVariablesMap = new HashMap<>();
        mailContentVariablesMap.put("activateUrl", StringUtils.getRootUrl(request) + "activate/" + user.getActivateCode());
        emailSender.sendEmail("[BCG BLOG] User Activation", new String[]{user.getEmail()}, "mail/register_active_token", mailContentVariablesMap);

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @RequestMapping(value = "activate/{activateCode}", method = RequestMethod.PUT)
    public ResponseEntity<User> activate(@PathVariable String activateCode) {
        User user = userService.findByActivateCode(UUIDGenerator.valueOf(activateCode));

        if (user == null) {
            return messageResponseEntity("激活码无效", HttpStatus.BAD_REQUEST);

        } else if (user.isActivated()) {
            return messageResponseEntity("用户已激活", HttpStatus.BAD_REQUEST);

        } else if (!user.isEnabled()) {
            return messageResponseEntity("用户已冻结", HttpStatus.BAD_REQUEST);
        }

        user.setActivated(true);
        userService.save(user);

        return new ResponseEntity(user, HttpStatus.OK);
    }

}
