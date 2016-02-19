package com.bianchunguang.blog;

import com.bianchunguang.blog.config.application.ApplicationStartedListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BlogApplication.class);
        springApplication.addListeners(new ApplicationStartedListener());
        springApplication.run(args);
    }
}
