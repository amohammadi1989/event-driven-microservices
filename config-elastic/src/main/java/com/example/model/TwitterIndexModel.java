package com.example.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
/**
 * Created By: Ali Mohammadi
 * Date: 12 Apr, 2022
 */
@Data
@Builder
@Document( indexName = "twitter-index")
public class TwitterIndexModel implements IndexModel{
  @JsonProperty
  private Long id;
  @JsonProperty
  private Long userId;
  @JsonProperty
  private Long createdAt;
  @JsonProperty
  private String text;

}
