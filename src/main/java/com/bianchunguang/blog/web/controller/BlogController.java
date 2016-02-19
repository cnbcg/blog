package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.persistence.services.BlogService;
import com.bianchunguang.blog.persistence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blogs")
public class BlogController extends BaseController {

    private @Autowired UserService userService;
    private @Autowired BlogService blogService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Blog> getBlogs(@RequestParam Map<String, String> requestParamMap) {
        User user = userService.findByAccount(requestParamMap.get("username"));

        if (user == null) {
            throw new NullPointerException("There is no user with username : " + requestParamMap.get("username"));
        }

        return user.getBlogs();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Blog createBlog(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, @RequestBody Blog blog) {

        User user = userService.findByAccount(principal.getUsername());
        blog.setAuthor(user);
        blogService.save(blog);

        return blog;
    }
}
