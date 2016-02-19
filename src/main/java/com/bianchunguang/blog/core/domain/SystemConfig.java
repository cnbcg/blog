package com.bianchunguang.blog.core.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_system_config")
public class SystemConfig extends AbstractEntity {

    private String name;
    private String value;

    public SystemConfig() {
        // required for jpa
    }

    public SystemConfig(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
