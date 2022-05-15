package com.example.batchtokafka.services.transfromer;
import com.example.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;
import twitter4j.Status;
/**
 * Created By: Ali Mohammadi
 * Date: 05 Apr, 2022
 */
@Component
public class TwitterStatusToAvroTransformer {
  public TwitterAvroModel getTwitterAvroModel(Status status){
    return TwitterAvroModel
    .newBuilder()
    .setId( status.getId() )
    .setCreatedAt( status.getCreatedAt().getTime() )
    .setText( status.getText() )
    .setUserId( status.getUser().getId() )
    .build();
  }
}
