package com.bianchunguang.blog.config.security;

import com.bianchunguang.blog.core.domain.oauth2.Client;
import com.bianchunguang.blog.persistence.services.oauth2.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.UUID;

@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private @Autowired ClientService clientService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientId -> {
            try {
                Client client = clientService.findOne(UUID.fromString(clientId));

                return new BaseClientDetails(client.getId().toString(), client.getResourceIds(),
                        client.getScope(), client.getAuthorizedGrantTypes(), client.getAuthorities());
            } catch (Exception e) {
                throw new OAuth2Exception(OAuth2Exception.INVALID_CLIENT, e);
            }
        });
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm("oauth/client");
    }

}