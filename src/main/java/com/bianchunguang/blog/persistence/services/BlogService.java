package com.bianchunguang.blog.persistence.services;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BlogService extends AbstractService<Blog, UUID> {

    Page<Blog> findByAuthor(User author, Pageable pageable);

    void increaseViewCount(UUID id);
}
