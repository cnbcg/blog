package com.bianchunguang.blog.persistence.services;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BlogCommentService extends AbstractService<BlogComment, UUID> {

    Page<BlogComment> findByBlog(Blog blog, Pageable pageable);
}
