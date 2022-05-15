package com.example.query.elastic.services;
import com.example.model.TwitterIndexModel;
import com.example.query.elastic.repository.TwitterElasticSearchQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created By: Ali Mohammadi
 * Date: 13 Apr, 2022
 */
@Service
public class TwitterElasticSearchQueryClientService {
  
  private final TwitterElasticSearchQueryRepository queryRepository;
  public TwitterElasticSearchQueryClientService(TwitterElasticSearchQueryRepository queryRepository) {
    this.queryRepository = queryRepository;
  }
  public List<TwitterIndexModel> getIndexModelByText(String text){
    return queryRepository.findByText( text );
  }
  public TwitterIndexModel getIndexModelById(Long id){
    return queryRepository.findById( id ).get();
  }
  public List<TwitterIndexModel> getIndexModels(){
    return (List<TwitterIndexModel>) queryRepository.findAll();
  }
}
