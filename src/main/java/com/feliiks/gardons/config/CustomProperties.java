package com.feliiks.gardons.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gardons")
public class CustomProperties {
    private String jwtSecret;

}