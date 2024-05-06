package org.project.brtservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.project.brtservice.configuration.ApplicationConfig;
import org.project.brtservice.requests.BrtRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueProducer {

    private final KafkaTemplate<String, BrtRequest> kafkaTemplate;
    private final ApplicationConfig applicationConfig;

    public void sendMessage(BrtRequest request) {
        log.error("shit");
        log.error(String.valueOf(request));
        log.info(applicationConfig.brtProducerTopic());
        kafkaTemplate.send(applicationConfig.brtProducerTopic(), request);
    }
}
