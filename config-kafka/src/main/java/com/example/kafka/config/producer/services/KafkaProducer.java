package com.example.kafka.config.producer.services;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;
public interface KafkaProducer<K extends Serializable,V extends SpecificRecordBase> {
  void send(String topicName,K key,V message);
}
