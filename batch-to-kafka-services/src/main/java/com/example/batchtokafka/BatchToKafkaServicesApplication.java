package com.example.batchtokafka;

import com.example.batchtokafka.services.runner.StreamRunner;
import com.example.config.services.BatchToKafkaConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class BatchToKafkaServicesApplication implements CommandLineRunner {

    private final BatchToKafkaConfig batchToKafkaConfig;
    private final StreamRunner streamRunner;

    public BatchToKafkaServicesApplication(BatchToKafkaConfig config,
                                           StreamRunner runner) {
        this.batchToKafkaConfig = config;
        this.streamRunner=runner;
    }

    public static void main(String[] args) {
        SpringApplication.run(BatchToKafkaServicesApplication.class, args);
    }

    @Override
    public void run(String... args)  throws Exception{
        System.out.println("App starts...");
        System.out.println(Arrays.toString(batchToKafkaConfig.getTwitterKeywords().toArray(new String[]{})));
        streamRunner.start();
    }
}
