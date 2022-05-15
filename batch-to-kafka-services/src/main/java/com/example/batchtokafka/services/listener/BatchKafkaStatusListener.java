package com.example.batchtokafka.services.listener;

import com.example.batchtokafka.services.transfromer.TwitterStatusToAvroTransformer;
import com.example.config.services.KafkaConfigData;
import com.example.kafka.avro.model.TwitterAvroModel;
import com.example.kafka.config.producer.services.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class BatchKafkaStatusListener extends StatusAdapter {
    private final KafkaConfigData kafkaConfigData;
    private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;
    //private final KafkaTemplate<String,TwitterAvroModel> kafkaTemplate;
    private final KafkaProducer kafkaProducer;
    
    public BatchKafkaStatusListener(KafkaConfigData kafkaConfigData,
                                    TwitterStatusToAvroTransformer twitterStatusToAvroTransformer,
                                     KafkaProducer kafkaProducer) {
        this.kafkaConfigData = kafkaConfigData;
        this.twitterStatusToAvroTransformer = twitterStatusToAvroTransformer;
        this.kafkaProducer = kafkaProducer;
    }
    
    @Override
    public void onStatus(Status status) {
        
        //System.out.println(MessageFormat.format("Batch status with text{%s}",status.getText()));
        TwitterAvroModel twitterAvroModel=
        twitterStatusToAvroTransformer.getTwitterAvroModel( status );

/*        kafkaTemplate.send("test",String.valueOf(twitterAvroModel.getId()),
                            twitterAvroModel );*/
        kafkaProducer.send( kafkaConfigData.getTopicName(),String.valueOf(twitterAvroModel.getId()),
                            twitterAvroModel );
    
    }
}
