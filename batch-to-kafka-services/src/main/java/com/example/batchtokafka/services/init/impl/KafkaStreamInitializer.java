package com.example.batchtokafka.services.init.impl;
import com.example.batchtokafka.services.init.StreamInitializer;
import com.example.config.services.KafkaConfigData;

import com.example.kafka.config.admin.KafkaAdminClient;
import org.springframework.stereotype.Component;
/**
 * Created By: Ali Mohammadi
 * Date: 05 Apr, 2022
 */
@Component
public class KafkaStreamInitializer implements StreamInitializer {

  private final KafkaConfigData kafkaConfigData;
  private final KafkaAdminClient kafkaAdminClient;
  
  public KafkaStreamInitializer(KafkaConfigData kafkaConfigData, KafkaAdminClient kafkaAdminClient) {
    this.kafkaConfigData = kafkaConfigData;
    this.kafkaAdminClient = kafkaAdminClient;
  }
  
  @Override
  public void init() {
    try {
      kafkaAdminClient.createTopics();
      kafkaAdminClient.checkSchemaRegistry();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
