package com.example.query.elastic.repository;
import com.example.model.TwitterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TwitterElasticSearchQueryRepository extends ElasticsearchRepository<TwitterIndexModel,Long> {
   List<TwitterIndexModel> findByText(String text);
}
