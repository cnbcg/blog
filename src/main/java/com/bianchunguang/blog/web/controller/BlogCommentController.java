package com.bianchunguang.blog.web.controller;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.BlogComment;
import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.persistence.services.BlogCommentService;
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
import java.util.UUID;

@RestController
@RequestMapping("/blogs/{blogId}/comments")
public class BlogCommentController extends BaseController {

    private @Autowired UserService userService;
    private @Autowired BlogService blogService;
    private @Autowired BlogCommentService blogCommentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<BlogComment>> getBlogComments(@PathVariable UUID blogId, @PageableDefault Pageable pageable) {

        Blog blog = blogService.findOne(blogId);

        if (blog == null || !blog.isEnabled()) {
            return messageResponseEntity("文章无效 : " + blogId, HttpStatus.BAD_REQUEST);
        }

        Page blogComments = blogCommentService.findByBlog(blog, pageable);

        return new ResponseEntity(blogComments, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BlogComment> createBlog(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, @PathVariable UUID blogId, @RequestBody @Valid BlogComment blogComment, BindingResult result) {

        if (result.getErrorCount() > 0) {
            return messageResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByAccount(principal.getUsername());
        blogComment.setAuthor(user);

        Blog blog = blogService.findOne(blogId);
        blogComment.setBlog(blog);

        blogCommentService.save(blogComment);

        return new ResponseEntity(blogComment, HttpStatus.OK);
    }

    @RequestMapping(value = "{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<BlogComment> deleteBlog(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, @PathVariable UUID commentId) {

        User user = userService.findByAccount(principal.getUsername());

        BlogComment blogComment = blogCommentService.findOne(commentId);

        if (!blogComment.getAuthor().equals(user)) {
            return messageResponseEntity("权限不足", HttpStatus.BAD_REQUEST);
        }

        blogComment.getChildren().stream().forEach(childComment -> childComment.setParent(blogComment.getParent()));

        blogComment.setEnabled(false);
        blogCommentService.save(blogComment);

        return new ResponseEntity(blogComment, HttpStatus.OK);
    }
}
