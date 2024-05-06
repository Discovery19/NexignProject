package org.project.hrs.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.project.hrs.requests.BrtRequest;
import org.project.hrs.requests.HrsRequest;
import org.project.hrs.requests.MonthRequest;
import org.project.hrs.service.HrsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final HrsService service;
    private final QueueProducer queueProducer;

    public void processNotification(BrtRequest notification) {
        System.out.println("gomic");
        log.info("process notification");
        log.info(String.valueOf(notification));
        var months = service.monthCheck(notification);
        if (months != null) {
            for (MonthRequest m : months) {
                sendNotification(m);
            }
        }
        var request = service.tariffCalculation(notification);
        sendNotification(request);
        //var authorizedUser = service.authorize(notification);
        //service.update(request);
        //service
        //authorizedUser.ifPresent(this::sendNotification);
    }

    public void sendNotification(HrsRequest notification) {
        log.error("send notification hrs");
        queueProducer.sendMessage(notification);
    }

    public void sendNotification(MonthRequest notification) {
        log.error("send notification hrs");
        queueProducer.sendMessage(notification);
    }
}

