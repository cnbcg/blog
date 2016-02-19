package com.bianchunguang.blog.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_blog")
public class Blog extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 20000)
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
