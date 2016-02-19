package com.bianchunguang.blog.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_authority")
@JsonIgnoreProperties(value = {"user"})
public class Authority extends AbstractEntity {

    public enum AuthorityType {
        USER, ADMIN
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    @ManyToMany(mappedBy = "authorities")
    private List<User> user = new ArrayList<User>();

    public AuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

}