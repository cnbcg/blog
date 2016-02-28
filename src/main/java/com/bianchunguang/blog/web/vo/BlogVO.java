package com.bianchunguang.blog.web.vo;

import com.bianchunguang.blog.core.domain.Blog;
import org.springframework.beans.BeanUtils;

public class BlogVO extends Blog {

    private long commentCount;
    private long viewCount;

    public BlogVO(Blog blog) {
        BeanUtils.copyProperties(blog, this);
        this.setCommentCount(blog.getBlogComments().size());
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

}
