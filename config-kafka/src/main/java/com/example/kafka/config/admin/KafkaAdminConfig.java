package com.example.kafka.config.admin;

import com.example.config.services.KafkaConfigData;
import com.example.kafka.avro.model.TwitterAvroModel;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

import java.util.Map;

@Configuration
@EnableRetry
public class KafkaAdminConfig {

    private final KafkaConfigData kafkaConfigData;
    public KafkaAdminConfig(KafkaConfigData kafkaConfigData) {
        this.kafkaConfigData = kafkaConfigData;
    }
    @Bean
    public AdminClient adminClient(){
        return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                kafkaConfigData.getBootstrapServers()));
    }

    @Bean
    public RetryTemplate retryTemplate(){
        return new RetryTemplate();
    }

}
