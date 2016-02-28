package com.bianchunguang.blog.persistence.repositories;

import com.bianchunguang.blog.core.domain.Blog;
import com.bianchunguang.blog.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BlogRepository extends JpaRepository<Blog, UUID> {

    Page<Blog> findByAuthor(User author, Pageable pageable);

    @Modifying
    @Query("update Blog blog set blog.viewCount = blog.viewCount + 1 where blog.id = ?1")
    void increaseViewCount(UUID id);
}
