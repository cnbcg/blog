package com.bianchunguang.blog.persistence.services.impl;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.persistence.repositories.BlogRepository;
import com.bianchunguang.blog.persistence.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BlogServiceImpl extends AbstractServiceImpl<Blog, UUID> implements BlogService {

    private @Autowired BlogRepository blogRepository;

    @Override
    protected JpaRepository<Blog, UUID> getRepository() {
        return blogRepository;
    }

    @Override
    public Page<Blog> findByAuthor(User author, Pageable pageable) {
        return blogRepository.findByAuthor(author, pageable);
    }
}
