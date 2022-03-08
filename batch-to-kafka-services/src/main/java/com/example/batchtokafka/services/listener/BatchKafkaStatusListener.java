package com.example.batchtokafka.services.listener;

import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

import java.text.MessageFormat;

@Component
public class BatchKafkaStatusListener extends StatusAdapter {
    @Override
    public void onStatus(Status status) {
        System.out.println(MessageFormat.format("Batch status with text{%s}",status.getText()));
    }
}
