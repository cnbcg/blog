package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.BlogComment;
import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.core.utils.Constants;
import com.bianchunguang.blog.persistence.services.BlogCommentService;
import com.bianchunguang.blog.persistence.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/blogs/comments")
public class BlogCommentController extends BaseController {

    private @Autowired BlogService blogService;
    private @Autowired BlogCommentService blogCommentService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<BlogComment> getBlogComments(@RequestParam(required = false) String blogId, @PageableDefault Pageable pageable) {
        if (blogId != null) {
            Blog blog = blogService.findOne(UUID.fromString(blogId));
            return blogCommentService.findByBlog(blog, pageable);

        } else {
            return blogCommentService.findAll(pageable);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public BlogComment createComment(@AuthenticationPrincipal User principal, @RequestParam("blogId") Blog blog, @RequestBody @Valid BlogComment blogComment, BindingResult result) {
        assertErrorIsNull(result);
        Assert.notNull(blog, "文章不存在");

        blogComment.setAuthor(principal);
        blogComment.setBlog(blog);

        return blogCommentService.save(blogComment);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public void deleteComment(@AuthenticationPrincipal User principal, @PathVariable("id") BlogComment blogComment) {
        Assert.isTrue(blogComment.getAuthor().equals(principal) || principal.hasRole(Constants.Role.ADMIN), "权限不足");

        blogComment.getChildren().stream().forEach(childComment -> childComment.setParent(blogComment.getParent()));
        blogCommentService.delete(blogComment.getId());
    }
}
