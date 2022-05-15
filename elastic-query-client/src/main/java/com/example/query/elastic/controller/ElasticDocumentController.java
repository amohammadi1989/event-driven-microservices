package com.example.query.elastic.controller;
import com.example.model.TwitterIndexModel;
import com.example.query.elastic.services.TwitterElasticSearchQueryClientService;
import com.example.services.TwitterElasticSearchServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Created By: Ali Mohammadi
 * Date: 13 Apr, 2022
 */
@RestController
@RequestMapping(value = "/documents")
public class ElasticDocumentController {
  public ElasticDocumentController(TwitterElasticSearchQueryClientService queryClientService) {
    this.queryClientService = queryClientService;
  }
  private final TwitterElasticSearchQueryClientService queryClientService;
  @GetMapping("/")
  public List<TwitterIndexModel> getAllDocuments(){
    return queryClientService.getIndexModels();
  }
  @GetMapping("/document/{id}")
  public TwitterIndexModel findDocumentByIds(@PathVariable("id") Long id){
    return queryClientService.getIndexModelById( id );
  }
  @GetMapping(value = "/document-text/{text}",produces = MediaType.APPLICATION_JSON_VALUE)
  
  public List<TwitterIndexModel> findDocumentByText(@PathVariable("text") String text){
    return queryClientService.getIndexModelByText( text );
  }
}
