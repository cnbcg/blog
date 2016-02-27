package com.bianchunguang.blog.web.vo;

import com.bianchunguang.blog.core.domain.Blog;
import org.springframework.beans.BeanUtils;

public class BlogVO extends Blog {

    private int commentCount;
    private int viewCount;

    public BlogVO(Blog blog) {
        BeanUtils.copyProperties(blog, this);
        this.setCommentCount(blog.getBlogComments().size());
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

}
