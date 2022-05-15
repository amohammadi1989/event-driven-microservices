package com.example.batchtokafka;

import com.example.batchtokafka.services.init.StreamInitializer;
import com.example.batchtokafka.services.runner.StreamRunner;
import com.example.config.services.BatchToKafkaProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class BatchToKafkaServicesApplication implements CommandLineRunner {
    
    private String topicName="test";
    
    
    private Integer partitions=1;
    
    
    private short replicationFactor=1;
    
    private final StreamRunner streamRunner;
    private final StreamInitializer streamInitializer;
    public BatchToKafkaServicesApplication(StreamRunner streamRunner,
                                           StreamInitializer streamInitializer) {
        this.streamRunner = streamRunner;
        this.streamInitializer = streamInitializer;
    }
    


    public static void main(String[] args) {
        SpringApplication.run(BatchToKafkaServicesApplication.class, args);
    }

    @Override
    public void run(String... args)  throws Exception{
        System.out.println("App starts...");
        streamInitializer.init();
        streamRunner.start();
    }
    
}
