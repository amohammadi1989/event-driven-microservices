package com.example.serviceregistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * Created By: Ali Mohammadi
 * Date: 02 May, 2022
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistry {
  public static void main(String[] args) {
    SpringApplication.run( ServiceRegistry.class,args );
  }
}
