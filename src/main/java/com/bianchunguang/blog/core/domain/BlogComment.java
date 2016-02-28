package com.bianchunguang.blog.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_blog_comment")
@JsonIgnoreProperties(value = {"children"})
public class BlogComment extends AbstractEntity {

    @Column(nullable = false, length = 500)
    @Size(min = 1, max = 500, message = "评论内容不能为空且不能大于500")
    private String content;

    @ManyToOne
    private User author;

    @ManyToOne
    private Blog blog;

    @OneToMany(mappedBy = "parent")
    private List<BlogComment> children = new ArrayList<>();

    @ManyToOne
    private BlogComment parent;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<BlogComment> getChildren() {
        return children;
    }

    public void setChildren(List<BlogComment> children) {
        this.children = children;
    }

    public BlogComment getParent() {
        return parent;
    }

    public void setParent(BlogComment parent) {
        this.parent = parent;
    }
}
