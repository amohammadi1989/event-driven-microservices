package com.example.kafka.config.producer.impl;
import com.example.kafka.avro.model.TwitterAvroModel;
import com.example.kafka.config.producer.services.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.text.MessageFormat;
/**
 * Created By: Ali Mohammadi
 * Date: 05 Apr, 2022
 */
@Component
public class TwitterKafkaProducer implements KafkaProducer<String, TwitterAvroModel> {
  private final KafkaTemplate<String,TwitterAvroModel> kafkaTemplate;
  
  public TwitterKafkaProducer(KafkaTemplate<String, TwitterAvroModel> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }
  
  @Override
  public void send(String topicName, String key, TwitterAvroModel twitterAvroModel) {
    ListenableFuture<SendResult<String,TwitterAvroModel>> listenableFuture=
    kafkaTemplate.send( topicName,String.valueOf(key),
                        twitterAvroModel );
    callback( listenableFuture );
  }
  
  private void callback(ListenableFuture<SendResult<String, TwitterAvroModel>> listenableFuture) {
    listenableFuture.addCallback( new ListenableFutureCallback<SendResult<String, TwitterAvroModel>>() {
      @Override
      public void onFailure(Throwable ex) {
        System.out.println("fail to send message to kafka.");
      }
  
      @Override
      public void onSuccess(SendResult<String, TwitterAvroModel> result) {
        String format=MessageFormat
        .format( "Recived new metadata, Topic:{} Partition{};Offset{};Timestamp;{}",
                 result.getRecordMetadata().topic(),result.getRecordMetadata().partition()
        ,result.getRecordMetadata().offset(),result.getRecordMetadata().timestamp()
        );
        System.out.println(format);
      }
    } );
  }
}
