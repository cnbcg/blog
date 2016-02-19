package com.bianchunguang.blog.config.security;

import com.bianchunguang.blog.core.domain.User;
import com.bianchunguang.blog.persistence.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.stream.Collectors;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private
    @Autowired
    UserService userService;

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 开放静态资源
                .antMatchers("/lib/**", "/themes/**").permitAll()
                // 开放公共视图
                .antMatchers("/", "/view**", "/directives**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().failureHandler(authenticationFailureHandler()).successHandler(authenticationSuccessHandler()).loginPage("/login").permitAll()
                .and().logout().logoutSuccessHandler(logoutSuccessHandler()).and().csrf().disable();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) ->
                response.getWriter().write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) ->
                response.setStatus(HttpStatus.OK.value());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider(userDetailsService(), passwordEncoder()));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return account -> {
            User user = userService.findByAccount(account);

            if (user == null || !user.isEnabled()) {
                throw new UsernameNotFoundException("User not exists");
            }

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.getAuthorities().stream().map(authority ->
                            (GrantedAuthority) () -> authority.toString()
                    ).collect(Collectors.toList()));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
