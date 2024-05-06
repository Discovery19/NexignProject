package org.project.cdrservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.cdrservice.config.ApplicationConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueProducer {

    private final KafkaTemplate<String, CdrRequest> kafkaTemplate;
    private final ApplicationConfig applicationConfig;

    public void sendMessage(CdrRequest request) {
        kafkaTemplate.send(applicationConfig.topic(), request);
    }
}
