package com.example.batchtokafka.services.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "batch-to-kafka-service")
public class BatchToKafkaConfig {
    private List<String> twitterKeywords;
}
