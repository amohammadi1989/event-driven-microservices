package com.example.query.elastic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * Created By: Ali Mohammadi
 * Date: 13 Apr, 2022
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class ElasticQueryApplication {
  public static void main(String[] args) {
    SpringApplication.run( ElasticQueryApplication.class,args );
  }
}
