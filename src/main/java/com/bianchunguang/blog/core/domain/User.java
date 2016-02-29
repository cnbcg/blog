package com.bianchunguang.blog.core.domain;

import com.bianchunguang.blog.core.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_user")
@JsonIgnoreProperties(value = {"blogs", "blogComments"})
public class User extends AbstractEntity {

    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9]+$", message = "昵称仅能包含字母数字汉字，长度2到10位")
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean activated;

    @Column(unique = true, columnDefinition = "BINARY(16)")
    private UUID activateCode;

    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private List<Blog> blogs = new ArrayList<>();

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private List<BlogComment> blogComments = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public UUID getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(UUID activateCode) {
        this.activateCode = activateCode;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<BlogComment> getBlogComments() {
        return blogComments;
    }

    public void setBlogComments(List<BlogComment> blogComments) {
        this.blogComments = blogComments;
    }

    public boolean hasRole(Constants.Role role) {
        return getRoles().stream().filter(userRole -> userRole.getCode().equals(role.toString())).findFirst().isPresent();
    }
}
