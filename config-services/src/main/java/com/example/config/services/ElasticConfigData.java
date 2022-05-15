package com.example.config.services;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * Created By: Ali Mohammadi
 * Date: 12 Apr, 2022
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-config")
public class ElasticConfigData {
  private String indexName;
  private String connectionUrl;
  private Integer connectTimeoutMs;
  private Integer socketTimeoutMs;
}
