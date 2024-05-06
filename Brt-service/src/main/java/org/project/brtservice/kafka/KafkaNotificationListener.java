package org.project.brtservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.brtservice.requests.CdrRequest;
import org.project.brtservice.requests.HrsRequest;
import org.project.brtservice.requests.MonthRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaNotificationListener {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "${app.cdr-listener-topic}",
            groupId = "cdr",
            containerFactory = "kafkaListenerContainerFactory")

    public void listenCdr(@Payload String request) throws JsonProcessingException {
        log.info(request);
        var obj = objectMapper.readValue(request, CdrRequest.class);
        notificationService.processNotification(obj);
    }
    @KafkaListener(topics = "${app.hrs-listener-topic}",
            groupId = "hrs",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenHrs(@Payload String request) throws JsonProcessingException{
        log.error("listen hrs");
        log.info(request);
        var obj = objectMapper.readValue(request, HrsRequest.class);
        notificationService.processNotification(obj);
    }

    @KafkaListener(topics = "${app.hrs-producer-month-topic}",
            groupId = "hrs",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenHrsMonth(@Payload String request) throws JsonProcessingException{
        log.error("listen month hrs");
        log.info(request);
        var obj = objectMapper.readValue(request, MonthRequest.class);
        notificationService.processNotification(obj);
    }


}

