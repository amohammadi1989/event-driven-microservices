package com.example.config.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "batch-to-kafka-service")
public class BatchToKafkaProperties {
    private List<String> twitterKeywords;
    private Integer mockMinTweetLength;
    private Integer mockMaxTweetLength;
    private Integer mockSleepMs;
}
