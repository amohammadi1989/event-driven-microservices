package com.example.kafkatoelastic.consumer.impl;

import com.example.kafka.avro.model.TwitterAvroModel;
import com.example.services.TwitterElasticSearchServices;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
public class TwitterKafkaConsumer {
  private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
  private final TwitterElasticSearchServices searchServices;
  
  
  public TwitterKafkaConsumer(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
                              TwitterElasticSearchServices searchServices) {
    this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    this.searchServices = searchServices;
  }
  @EventListener
  public void onAppStarted(ApplicationStartedEvent event){
    kafkaListenerEndpointRegistry.getListenerContainer( "twitter-topic-consumer" ).start();
  }
  @KafkaListener(id = "twitter-topic-consumer", topics = "twitter-topic")
  public void receive(@Payload List<TwitterAvroModel> messages,
                      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<?> keys,
                      @Header(KafkaHeaders.OFFSET) List<?> offset
  ) {
    try {
      String format=MessageFormat
      .format( "{0} Number message received .! keys:{1},offset:{2}",
               String.valueOf(messages.size()),
               String.valueOf(keys.size()),
               String.valueOf( offset.size() )
      );
      System.out.println(format);
    
      System.out.println("Message contents:");
    
      messages.parallelStream().forEach( System.out::println );
      searchServices.saveDocument( messages );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
