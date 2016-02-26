package com.bianchunguang.blog.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_blog")
@JsonIgnoreProperties(value = {"blogComments"})
public class Blog extends AbstractEntity {

    @Column(nullable = false)
    @NotNull(message = "标题不能为空")
    private String title;

    @Column(nullable = false, length = 20000)
    @Size(min = 1, max = 20000, message = "正文内容不能为空且不能大于20000")
    private String content;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "blog", orphanRemoval = true)
    private List<BlogComment> blogComments = new ArrayList();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public List<BlogComment> getBlogComments() {
        return blogComments;
    }

    public void setBlogComments(List<BlogComment> blogComments) {
        this.blogComments = blogComments;
    }
}
