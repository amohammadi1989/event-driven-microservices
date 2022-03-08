package com.example.batchtokafka.services.runner.impl;

import com.example.batchtokafka.services.config.BatchToKafkaConfig;
import com.example.batchtokafka.services.listener.BatchKafkaStatusListener;
import com.example.batchtokafka.services.runner.StreamRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.text.MessageFormat;
import java.util.Arrays;

@Component
@ConditionalOnProperty(name="twitter-to-kafka-service.enable-mock-tweets",havingValue = "false",matchIfMissing = true)
public class BatchToKafkaStreamRunner implements StreamRunner {

    private final BatchToKafkaConfig batchToKafkaConfig;
    private final BatchKafkaStatusListener batchKafkaStatusListener;
    private final TwitterStream twitterStream = TwitterStreamFactory.getSingleton();

    public BatchToKafkaStreamRunner(BatchToKafkaConfig batchToKafkaConfig,
                                    BatchKafkaStatusListener batchKafkaStatusListener) {
        this.batchToKafkaConfig = batchToKafkaConfig;
        this.batchKafkaStatusListener = batchKafkaStatusListener;
    }

    @Override
    public void start()
    {
        twitterStream.addListener(batchKafkaStatusListener);
        String[] keywords=batchToKafkaConfig.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery=new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        String out=MessageFormat.format("Started filtered twitter steam for keywords{0}",
                Arrays.toString(keywords));
        System.out.println(out);
    }
}
