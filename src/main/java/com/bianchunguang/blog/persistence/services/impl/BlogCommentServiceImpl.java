package com.bianchunguang.blog.persistence.services.impl;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.BlogComment;
import com.bianchunguang.blog.persistence.repositories.BlogCommentRepository;
import com.bianchunguang.blog.persistence.services.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class BlogCommentServiceImpl extends AbstractServiceImpl<BlogComment, UUID> implements BlogCommentService {

    private @Autowired BlogCommentRepository blogCommentRepository;

    @Override
    protected JpaRepository<BlogComment, UUID> getRepository() {
        return blogCommentRepository;
    }

    @Override
    public Page<BlogComment> findByBlog(Blog blog, Pageable pageable) {
        return blogCommentRepository.findByBlogAndEnabled(blog, true, pageable);
    }
}
