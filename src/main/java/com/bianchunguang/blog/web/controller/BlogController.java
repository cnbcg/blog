package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.core.domain.Authority;
import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.BlogComment;
import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.persistence.services.BlogService;
import com.bianchunguang.blog.persistence.services.UserService;
import com.bianchunguang.blog.web.vo.BlogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blogs")
public class BlogController extends BaseController {

    private @Autowired UserService userService;
    private @Autowired BlogService blogService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Blog>> getBlogs(@RequestParam Map<String, String> requestParamMap, @PageableDefault Pageable pageable) {
        User user = userService.findByAccount(requestParamMap.get("username"));

        if (user == null) {
            return messageResponseEntity("用户不存在 : " + requestParamMap.get("username"), HttpStatus.BAD_REQUEST);
        }

        Page<Blog> blogs = blogService.findByAuthor(user, pageable);

        List blogVOList = blogs.getContent().stream().map(blog -> {
            String content = blog.getContent();
            blog.setContent(content.length() > 300 ? content.substring(0, 300) + " ..." : content);
            return new BlogVO(blog);
        }).collect(Collectors.toList());

        blogs = new PageImpl(blogVOList, pageable, blogs.getTotalElements());

        return new ResponseEntity(blogs, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Blog> getBlog(@PathVariable UUID id) {
        Blog blog = blogService.findOne(id);

        if (blog == null) {
            return messageResponseEntity("文章不存在：" + id, HttpStatus.BAD_REQUEST);
        }

        blogService.increaseViewCount(blog.getId());
        return new ResponseEntity(new BlogVO(blog), HttpStatus.OK);
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

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Blog> updateBlog(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, @PathVariable UUID id, @RequestBody @Valid Blog blog, BindingResult result) {

        if (result.getErrorCount() > 0) {
            return messageResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        Blog dbBlog = blogService.findOne(id);

        User user = userService.findByAccount(principal.getUsername());

        if (!blog.getAuthor().equals(user) && !user.hasAuthority(Authority.AuthorityType.ADMIN)) {
            return messageResponseEntity("权限不足", HttpStatus.BAD_REQUEST);
        }

        dbBlog.setTitle(blog.getTitle());
        dbBlog.setContent(blog.getContent());
        blogService.save(dbBlog);

        return new ResponseEntity(dbBlog, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Blog> deleteBlog(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, @PathVariable UUID id) {

        User user = userService.findByAccount(principal.getUsername());

        Blog blog = blogService.findOne(id);

        if (!blog.getAuthor().equals(user) && !user.hasAuthority(Authority.AuthorityType.ADMIN)) {
            return messageResponseEntity("权限不足", HttpStatus.BAD_REQUEST);
        }

        blogService.delete(blog.getId());

        return new ResponseEntity(blog, HttpStatus.OK);
    }
}
