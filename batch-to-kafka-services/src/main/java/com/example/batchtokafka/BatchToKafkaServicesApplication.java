package com.example.batchtokafka;

import com.example.batchtokafka.services.config.BatchToKafkaConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class BatchToKafkaServicesApplication implements CommandLineRunner {

    private final BatchToKafkaConfig batchToKafkaConfig;

    public BatchToKafkaServicesApplication(BatchToKafkaConfig config) {
        this.batchToKafkaConfig = config;
    }

    public static void main(String[] args) {
        SpringApplication.run(BatchToKafkaServicesApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        System.out.println("App starts...");
        System.out.println(Arrays.toString(batchToKafkaConfig.getTwitterKeywords().toArray(new String[]{})));
    }
}
