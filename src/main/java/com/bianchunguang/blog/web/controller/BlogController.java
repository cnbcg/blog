package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.core.utils.Constants;
import com.bianchunguang.blog.persistence.services.BlogService;
import com.bianchunguang.blog.persistence.services.UserService;
import com.bianchunguang.blog.web.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blogs")
public class BlogController extends BaseController {

    private @Autowired UserService userService;
    private @Autowired BlogService blogService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Blog> getBlogs(@RequestParam(required = false) String userId, @PageableDefault Pageable pageable) {

        Page<Blog> blogs = null;

        if (StringUtils.isEmpty(userId)) {
            blogs = blogService.findAll(pageable);

        } else {
            User user = userService.findByAccount(userId);
            Assert.notNull(user, "用户不存在: " + userId);
            blogs = blogService.findByAuthor(user, pageable);
        }

        List<Blog> blogVOList = blogs.getContent().stream().map(blog -> {
            String content = blog.getContent();
            blog.setContent(content.length() > 300 ? content.substring(0, 300) + " ..." : content);
            return new BlogVO(blog);
        }).collect(Collectors.toList());

        blogs = new PageImpl<>(blogVOList, pageable, blogs.getTotalElements());

        return blogs;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Blog getBlog(@PathVariable("id") Blog blog) {
        blogService.increaseViewCount(blog.getId());
        return new BlogVO(blog);
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Blog createBlog(@AuthenticationPrincipal User principal, @RequestBody @Valid Blog blog, BindingResult result) {
        assertErrorIsNull(result);
        blog.setAuthor(principal);
        return blogService.save(blog);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public Blog updateBlog(@AuthenticationPrincipal User principal, @PathVariable("id") Blog dbBlog, @RequestBody @Valid Blog blog, BindingResult result) {
        assertErrorIsNull(result);

        Assert.isTrue(dbBlog.getAuthor().equals(principal) || principal.hasRole(Constants.Role.ADMIN), "权限不足");

        dbBlog.setTitle(blog.getTitle());
        dbBlog.setContent(blog.getContent());
        blogService.save(dbBlog);

        return dbBlog;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public void deleteBlog(@AuthenticationPrincipal User principal, @PathVariable("id") Blog blog) {
        Assert.isTrue(blog.getAuthor().equals(principal) || principal.hasRole(Constants.Role.ADMIN), "权限不足");

        blogService.delete(blog.getId());
    }
}
