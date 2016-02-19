package com.bianchunguang.blog.config.persistence;

import com.bianchunguang.blog.persistence.repositories.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableJpaAuditing
@EnableSpringDataWebSupport
@EnableTransactionManagement
public class JPAConfig {
}
