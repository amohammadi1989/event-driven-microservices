package com.example.services;
import com.example.kafka.avro.model.TwitterAvroModel;
import com.example.model.TwitterIndexModel;
import com.example.repository.TwitterElasticsearchIndexRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Created By: Ali Mohammadi
 * Date: 12 Apr, 2022
 */
@Service
public class TwitterElasticSearchServices {
  private final TwitterElasticsearchIndexRepository repository;
  
  public TwitterElasticSearchServices(TwitterElasticsearchIndexRepository repository) {
    this.repository = repository;
  }
  public void saveDocument(List<TwitterAvroModel> twitterAvroModels){
    List<TwitterIndexModel> twitterIndexModels= twitterAvroModels.
    parallelStream().map( a->{
      TwitterIndexModel indexModel=TwitterIndexModel.builder()
      .id( a.getId() ).createdAt( a.getCreatedAt() )
      .text( a.getText() ).userId( a.getUserId() ).build();
      return indexModel;
    } ).collect( Collectors.toList());
  
    try {
      repository.saveAll( twitterIndexModels );
    } catch (Exception e) {
    
    }
  
  
  }
}
