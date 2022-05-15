package com.example.config;
import com.example.config.services.ElasticConfigData;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
/**
 * Created By: Ali Mohammadi
 * Date: 12 Apr, 2022
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
  public ElasticSearchConfig(ElasticConfigData elasticConfigData) {
    this.elasticConfigData = elasticConfigData;
  }
  
  private final ElasticConfigData elasticConfigData;
  
  @Override
  public RestHighLevelClient elasticsearchClient() {
    UriComponents serverUri= UriComponentsBuilder.fromHttpUrl( elasticConfigData.getConnectionUrl() )
    .build();
    return new RestHighLevelClient(
    RestClient.builder( new HttpHost(
    Objects.requireNonNull( serverUri.getHost()),
    serverUri.getPort(),
    serverUri.getScheme()
    ) ).setRequestConfigCallback(
    requestConfigsBuilder->requestConfigsBuilder.
    setConnectTimeout( elasticConfigData.getConnectTimeoutMs() ).
    setSocketTimeout( elasticConfigData.getSocketTimeoutMs() )
    )
    );
  }
  @Bean
  public ElasticsearchOperations elasticsearchOperations(){
    return new ElasticsearchRestTemplate( elasticsearchClient() );
  }
}
