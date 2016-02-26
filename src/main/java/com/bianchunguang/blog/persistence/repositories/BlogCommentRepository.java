package com.bianchunguang.blog.persistence.repositories;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlogCommentRepository extends JpaRepository<BlogComment, UUID> {

    Page<BlogComment> findByBlogAndEnabled(Blog blog, Boolean enabled, Pageable pageable);
}
