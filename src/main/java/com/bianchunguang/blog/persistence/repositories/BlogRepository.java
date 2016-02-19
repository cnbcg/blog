package com.bianchunguang.blog.persistence.repositories;

import com.bianchunguang.blog.core.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlogRepository extends JpaRepository<Blog, UUID> {
}
