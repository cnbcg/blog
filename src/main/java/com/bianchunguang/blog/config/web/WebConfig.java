package com.bianchunguang.blog.config.web;

import com.bianchunguang.blog.core.utils.Constants;
import com.bianchunguang.blog.persistence.services.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final String DEFAULT_THEME_BASE_NAME = "static/themes/";

    private @Autowired SystemConfigService systemConfigService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
        registry.addInterceptor(themeChangeInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        String defaultLocal = systemConfigService.findByName(Constants.SystemConfigConstants.UI_LOCAL.toString()).getValue();
        cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString(defaultLocal));
        return cookieLocaleResolver;
    }

    @Bean
    public ThemeSource themeSource() {
        ResourceBundleThemeSource resourceBundleThemeSource = new ResourceBundleThemeSource();
        resourceBundleThemeSource.setBasenamePrefix(DEFAULT_THEME_BASE_NAME);
        return resourceBundleThemeSource;
    }

    @Bean
    public HandlerInterceptor themeChangeInterceptor() {
        return new ThemeChangeInterceptor();
    }

    @Bean
    public ThemeResolver themeResolver() {
        final CookieThemeResolver cookieThemeResolver = new CookieThemeResolver();
        String defaultTheme = systemConfigService.findByName(Constants.SystemConfigConstants.UI_THEME.toString()).getValue();
        cookieThemeResolver.setDefaultThemeName(defaultTheme);
        return cookieThemeResolver;
    }

    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
        return openEntityManagerInViewFilter;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(openEntityManagerInViewFilter());
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

}