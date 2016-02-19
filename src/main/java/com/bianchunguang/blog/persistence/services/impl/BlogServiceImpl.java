package com.bianchunguang.blog.persistence.services.impl;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.persistence.repositories.BlogRepository;
import com.bianchunguang.blog.persistence.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlogServiceImpl extends AbstractServiceImpl<Blog, UUID> implements BlogService {

    private @Autowired BlogRepository blogRepository;

    @Override
    protected JpaRepository<Blog, UUID> getRepository() {
        return blogRepository;
    }
}
