package org.project.hrs.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.hrs.configuration.ApplicationConfig;
import org.project.hrs.requests.HrsRequest;
import org.project.hrs.requests.MonthRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueProducer {

    private final KafkaTemplate<String, HrsRequest> kafkaTemplate;
    private final ApplicationConfig applicationConfig;

    public void sendMessage(HrsRequest request) {
        log.error("send message hrs");
        log.error(String.valueOf(request));
        log.info(applicationConfig.hrsProducerTopic());
        kafkaTemplate.send(applicationConfig.hrsProducerTopic(), request);
    }
    public void sendMessage(MonthRequest monthRequest){
        log.error("send message hrs month");
        log.info(applicationConfig.hrsProducerMonthNotifTopic(), monthRequest);
    }
}
