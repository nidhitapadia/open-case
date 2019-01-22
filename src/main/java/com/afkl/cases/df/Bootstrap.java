package com.afkl.cases.df;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

/**
 * The spring boot runner.
 */
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
)
@EnableConfigurationProperties
public class Bootstrap {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Bootstrap.class, args);
    }

}
