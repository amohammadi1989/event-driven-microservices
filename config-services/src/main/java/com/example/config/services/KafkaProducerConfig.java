package com.example.config.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix="kafka-producer-config")
public class KafkaProducerConfig {
    private String keySerializerClass;
    private String valueSerializerClass;
    private String compressionType;
    private String ack;
    private Integer batchSize;
    private Integer batchSizeBoostFactor;
    private Integer linerMs;
    private Integer requestTimeoutMs;
    private Integer retryCount;
}
