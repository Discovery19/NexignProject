package org.project.hrs.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.hrs.requests.BrtRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaNotificationListener {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "${app.brt-listener-topic}",
            groupId = "hrs",
            containerFactory = "kafkaListenerContainerFactory")

    public void listenBrt(@Payload String request) throws JsonProcessingException {
        log.info(request);
        var obj = objectMapper.readValue(request, BrtRequest.class);
        log.info(obj.toString());
        notificationService.processNotification(obj);
    }
//    @KafkaListener(topics = "${app.hrs-listener-topic}",
//            groupId = "hrs",
//            containerFactory = "kafkaListenerContainerFactory")
//    public void listenHrs(@Payload String request) throws JsonProcessingException{
//        log.info(request);//TODO hrs topic listener
//        var obj = objectMapper.readValue(request, CdrRequest.class);
//        notificationService.processNotification(obj);
//    }



}

