package org.project.hrs.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.project.hrs.requests.BrtRequest;
import org.project.hrs.requests.HrsRequest;
import org.project.hrs.requests.MonthRequest;
import org.project.hrs.service.HrsService;
import org.springframework.stereotype.Service;

/**
 * NotificationService представляет сервис для обработки уведомлений, связанных с системой управления ресурсами (HRS).
 * Он принимает запросы BrtRequest, обрабатывает их с помощью сервиса HrsService и отправляет уведомления через
 * QueueProducer. Метод processNotification() обрабатывает уведомления, вызывая методы сервиса для проверки месяцев
 * и расчета тарифов, а затем отправляет соответствующие уведомления. Методы sendNotification() отправляют
 * уведомления через QueueProducer.
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final HrsService service;
    private final QueueProducer queueProducer;

    public void processNotification(BrtRequest notification) {
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

