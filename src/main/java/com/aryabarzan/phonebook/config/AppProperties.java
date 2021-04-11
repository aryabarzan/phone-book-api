package com.aryabarzan.phonebook.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "github")
@Getter
public class AppProperties {
    private final Github github = new Github();

    @Getter
    @Setter
    public static class Github {
        private String username;
        private String token;
    }
}