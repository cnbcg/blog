package com.bianchunguang.blog.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_blog")
public class Blog extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 20000)
    @Size(min = 1, max = 20000, message = "正文内容超长")
    private String content;

    @ManyToOne
    private User author;

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
}
