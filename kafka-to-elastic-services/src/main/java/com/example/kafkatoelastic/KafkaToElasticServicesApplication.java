package com.example.kafkatoelastic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * Created By: Ali Mohammadi
 * Date: 10 Apr, 2022
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class KafkaToElasticServicesApplication {
  public static void main(String[] args) {
    SpringApplication.run( KafkaToElasticServicesApplication.class,args );
  }
}
