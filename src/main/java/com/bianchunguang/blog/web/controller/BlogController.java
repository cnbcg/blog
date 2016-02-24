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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blogs")
public class BlogController extends BaseController {

    private @Autowired UserService userService;
    private @Autowired BlogService blogService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Blog>> getBlogs(@RequestParam Map<String, String> requestParamMap, @PageableDefault Pageable pageable) {
        User user = userService.findByAccount(requestParamMap.get("username"));

        if (user == null) {
            return messageResponseEntity("There is no user with username : " + requestParamMap.get("username"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(blogService.findByAuthor(user, pageable), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Blog> createBlog(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, @RequestBody @Valid Blog blog, BindingResult result) {

        if (result.getErrorCount() > 0) {
            return messageResponseEntity(result.getAllErrors().stream().map(error -> error.getDefaultMessage()).reduce((error1, error2) -> error1 + "," + error2).get(), HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByAccount(principal.getUsername());
        blog.setAuthor(user);
        blogService.save(blog);

        return new ResponseEntity(blog, HttpStatus.OK);
    }
}
