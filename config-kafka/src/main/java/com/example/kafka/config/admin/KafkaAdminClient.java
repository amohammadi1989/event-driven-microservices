package com.example.kafka.config.admin;
import com.example.config.services.KafkaConfigData;
import com.example.config.services.RetryConfig;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Configuration
public class KafkaAdminClient {
  
  private final KafkaConfigData kafkaConfigData;
  
  private final RetryConfig retryConfigData;
  
  private final AdminClient adminClient;
  
  private final RetryTemplate retryTemplate;
  
  private final WebClient webClient;
  
  
  public KafkaAdminClient(KafkaConfigData config,
                          RetryConfig retryConfig,
                          AdminClient client,
                          RetryTemplate template,
                          WebClient webClient) {
    this.kafkaConfigData = config;
    this.retryConfigData = retryConfig;
    this.adminClient = client;
    this.retryTemplate = template;
    this.webClient = webClient;
  }
  @Bean
  public NewTopic newTopic(){
    return new NewTopic( kafkaConfigData.getTopicName().trim(),
                         kafkaConfigData.getNumOfPartitions(),
                         kafkaConfigData.getReplicationFactor().shortValue() );
  }
  
  public void createTopics() {
    CreateTopicsResult createTopicsResult;
    try {
      createTopicsResult = retryTemplate.execute(this::doCreateTopics);
    } catch (Throwable t) {
      throw new RuntimeException("Reached max number of retry for creating kafka topic(s)!", t);
    }
    checkTopicsCreated();
  }
  
  public void checkTopicsCreated() {
    Collection<TopicListing> topics = getTopics();
    int retryCount = 1;
    Integer maxRetry = retryConfigData.getMaxAttempts();
    int multiplier = retryConfigData.getMultiplier().intValue();
    Long sleepTimeMs = retryConfigData.getSleepTimeMs().longValue();
    for (String topic : kafkaConfigData.getTopicNamesToCreate()) {
      while (!isTopicCreated(topics, topic)) {
        checkMaxRetry(retryCount++, maxRetry);
        sleep(sleepTimeMs);
        sleepTimeMs *= multiplier;
        topics = getTopics();
      }
    }
  }
  
  public void checkSchemaRegistry() {
    int retryCount = 1;
    Integer maxRetry = retryConfigData.getMaxAttempts();
    int multiplier = retryConfigData.getMultiplier().intValue();
    Long sleepTimeMs = retryConfigData.getSleepTimeMs().longValue();
    while (!getSchemaRegistryStatus().is2xxSuccessful()) {
      checkMaxRetry(retryCount++, maxRetry);
      sleep(sleepTimeMs);
      sleepTimeMs *= multiplier;
    }
  }
  
  private HttpStatus getSchemaRegistryStatus() {
    try {
      return webClient
      .method(HttpMethod.GET)
      .uri(kafkaConfigData.getSchemaRegistryUrl())
      .exchange()
      .map(ClientResponse::statusCode)
      .block();
    } catch (Exception e) {
      return HttpStatus.SERVICE_UNAVAILABLE;
    }
  }
  
  
  private void sleep(Long sleepTimeMs) {
    try {
      Thread.sleep(sleepTimeMs);
    } catch (InterruptedException e) {
      throw new RuntimeException("Error while sleeping for waiting new created topics!!");
    }
  }
  
  private void checkMaxRetry(int retry, Integer maxRetry) {
    if (retry > maxRetry) {
      throw new RuntimeException("Reached max number of retry for reading kafka topic(s)!");
    }
  }
  
  private boolean isTopicCreated(Collection<TopicListing> topics, String topicName) {
    if (topics == null) {
      return false;
    }
    return topics.stream().anyMatch(topic -> topic.name().equals(topicName));
  }
  
  private CreateTopicsResult doCreateTopics(RetryContext retryContext) {
    List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
    List<NewTopic> kafkaTopics = topicNames.stream().map(topic ->
       TopicBuilder.name( "thing1")
      .partitions(10)
      .replicas(3)
      .compact()
      .build()
    ).collect(Collectors.toList());
    return adminClient.createTopics(kafkaTopics);
  }
  
  
  private Collection<TopicListing> getTopics() {
    Collection<TopicListing> topics=null;
  
    try {
      topics = retryTemplate.execute(this::doGetTopics);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  
    return topics;
  }
  
  private Collection<TopicListing> doGetTopics(RetryContext retryContext)
  throws  InterruptedException {
  
    Collection<TopicListing> topics = null;
    try {
      topics = adminClient.listTopics().listings().get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  
  
    return topics;
  }
}
