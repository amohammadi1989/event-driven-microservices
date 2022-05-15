package com.example.batchtokafka.services.runner.impl;

import com.example.batchtokafka.services.listener.BatchKafkaStatusListener;
import com.example.batchtokafka.services.runner.StreamRunner;
import com.example.config.services.BatchToKafkaProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.text.MessageFormat;
import java.util.Arrays;

//@Component
//@ConditionalOnProperty(name="batch-to-kafka-service.enable-mock-tweets",havingValue = "false",
//matchIfMissing = true)
public class BatchToKafkaStreamRunner implements StreamRunner {

    private final BatchToKafkaProperties BatchToKafkaProperties;
    private final BatchKafkaStatusListener batchKafkaStatusListener;
    private final TwitterStream twitterStream = TwitterStreamFactory.getSingleton();

    public BatchToKafkaStreamRunner(BatchToKafkaProperties BatchToKafkaProperties,
                                    BatchKafkaStatusListener batchKafkaStatusListener) {
        this.BatchToKafkaProperties = BatchToKafkaProperties;
        this.batchKafkaStatusListener = batchKafkaStatusListener;
    }

    @Override
    public void start()
    {
        twitterStream.addListener(batchKafkaStatusListener);
        String[] keywords=BatchToKafkaProperties.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery=new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        String out=MessageFormat.format("Started filtered twitter steam for keywords{0}",
                Arrays.toString(keywords));
        System.out.println(out);
    }
}
