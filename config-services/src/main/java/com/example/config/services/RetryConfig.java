package com.example.config.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "retry-config")
@Configuration
public class RetryConfig {

    private Integer initialIntervalMs;
    private Integer maxIntervalMs;
    private Integer multiplier;
    private Integer maxAttempts;
    private Integer sleepTimeMs;
}
