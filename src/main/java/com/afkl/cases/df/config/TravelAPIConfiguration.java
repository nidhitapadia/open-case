package com.afkl.cases.df.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * The Travel API configuration class
 */
@Configuration
@EnableOAuth2Client
@EnableAsync
@ComponentScan("com.afkl.cases.df")
public class TravelAPIConfiguration {

    /**
     * Bean OAuth2ProtectedResourceDetails
     *
     * @return the OAuth2ProtectedResourceDetails
     */
    @Bean
    @ConfigurationProperties("simple.travel.client")
    public OAuth2ProtectedResourceDetails resourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    /**
     * Bean OAuth2ClientContext.
     *
     * @return the OAuth2ClientContext
     */
    @Bean
    public OAuth2ClientContext oAuth2ClientContext() {
        return new DefaultOAuth2ClientContext();
    }

    /**
     * Bean oAuth2RestTemplate
     *
     * @return the OAuth2RestTemplate
     */
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate() {
        return new OAuth2RestTemplate(resourceDetails(), oAuth2ClientContext());
    }
}
