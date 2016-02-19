package com.bianchunguang.blog.config;

import com.bianchunguang.blog.core.domain.SystemConfig;
import com.bianchunguang.blog.core.utils.Constants;
import com.bianchunguang.blog.persistence.services.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;


@Configuration
public class BootConfig implements EmbeddedServletContainerCustomizer {

    private @Autowired Environment environment;
    private @Autowired SystemConfigService systemConfigService;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        systemConfigService.save(Arrays.asList(Constants.SystemConfigConstants.values()).stream().filter(config -> !systemConfigService.exist(config.toString()))
                .map(config -> {
                    String name = config.toString(), value = environment.getProperty(name);
                    if (StringUtils.isEmpty(value) && config.isRequire()) throw new IllegalArgumentException("No property found : " + name);
                    if (StringUtils.isEmpty(value) && !config.isRequire()) value = config.getDefaultValue();
                    return new SystemConfig(name, value);
                }).collect(Collectors.toList()));
    }

}
