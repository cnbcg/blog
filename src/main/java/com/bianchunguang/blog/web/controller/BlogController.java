package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.persistence.services.BlogService;
import com.bianchunguang.blog.persistence.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/blogs")
public class BlogController extends BaseController {

    private @Autowired UserService userService;
    private @Autowired BlogService blogService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Blog>> getBlogs(@RequestParam Map<String, String> requestParamMap, @PageableDefault Pageable pageable) {
        User user = userService.findByAccount(requestParamMap.get("username"));

        if (user == null || !user.isEnabled() || !user.isActivated()) {
            return messageResponseEntity("无效用户 : " + requestParamMap.get("username"), HttpStatus.BAD_REQUEST);
        }

        Page<Blog> blogs = blogService.findByAuthor(user, pageable);
        blogs.getContent().stream().forEach(blog -> {
            String content = blog.getContent();
            blog.setContent(content.length() > 400 ? content.substring(0, 400) + " ..." : content);
        });

        return new ResponseEntity(blogs, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Blog> getBlog(@PathVariable UUID id) {
        return new ResponseEntity(blogService.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Blog> createBlog(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, @RequestBody @Valid Blog blog, BindingResult result) {

        if (result.getErrorCount() > 0) {
            return messageResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByAccount(principal.getUsername());
        blog.setAuthor(user);
        blogService.save(blog);

        return new ResponseEntity(blog, HttpStatus.OK);
    }
}
