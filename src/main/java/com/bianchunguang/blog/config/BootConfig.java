package com.bianchunguang.blog.config;

import com.bianchunguang.blog.core.domain.SystemConfig;
import com.bianchunguang.blog.core.utils.Constants;
import com.bianchunguang.blog.persistence.services.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Configuration
public class BootConfig implements EmbeddedServletContainerCustomizer {

    private @Autowired Environment environment;
    private @Autowired SystemConfigService systemConfigService;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        try {
            container.setAddress(InetAddress.getByName("www.bianchunguang.com"));

        } catch (UnknownHostException e) {
            throw new RuntimeException("域名无效:" + e.getMessage());
        }
        systemConfigService.save(Arrays.asList(Constants.SystemConfigConstants.values()).stream().filter(config -> !systemConfigService.exist(config.toString()))
                .map(config -> {
                    String name = config.toString(), value = environment.getProperty(name);
                    if (StringUtils.isEmpty(value) && config.isRequire()) throw new IllegalArgumentException("No property found : " + name);
                    if (StringUtils.isEmpty(value) && !config.isRequire()) value = config.getDefaultValue();
                    return new SystemConfig(name, value);
                }).collect(Collectors.toList()));
    }

}
