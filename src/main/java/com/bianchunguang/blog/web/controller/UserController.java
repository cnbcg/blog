package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.core.utils.Constants;
import com.bianchunguang.blog.core.utils.EmailSender;
import com.bianchunguang.blog.core.utils.StringUtils;
import com.bianchunguang.blog.persistence.services.RoleService;
import com.bianchunguang.blog.persistence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    private @Autowired UserService userService;
    private @Autowired RoleService roleService;
    private @Autowired EmailSender emailSender;
    private @Autowired PasswordEncoder passwordEncoder;

    @RequestMapping("")
    public List<User> getAllUser() {
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody @Valid User user, BindingResult result, HttpServletRequest request) {

        Assert.isNull(userService.findByAccount(user.getEmail()), "邮箱已存在");
        Assert.isTrue(user.getPassword() != null && user.getPassword().matches("^.*(?=.*\\d)(?=.*[a-zA-Z]).*"), "密码需同时包含字母和数字，长度6到16位");

        assertErrorIsNull(result);

        user.setActivateCode(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleService.findByCode(userService.count() == 0 ? Constants.Role.ADMIN : Constants.Role.USER)));

        Map<String, Object> mailContentVariablesMap = new HashMap<>();
        mailContentVariablesMap.put("activateUrl", StringUtils.getRootUrl(request) + "activate/" + user.getActivateCode());

        Assert.isTrue(emailSender.sendEmail("[BCG BLOG] User Activation", user.getEmail(), "mail/register_active_token", mailContentVariablesMap), "账户激活邮件发送失败，请确认邮箱重试。");

        return userService.save(user);
    }

    @RequestMapping(value = "activate/{activateCode}", method = RequestMethod.PUT)
    public User activate(@PathVariable String activateCode) {
        User user = userService.findByActivateCode(UUID.fromString(activateCode));

        Assert.notNull(user, "激活码无效");
        Assert.isTrue(!user.isActivated(), "用户已激活");

        user.setActivated(true);
        userService.save(user);

        return user;
    }

}
