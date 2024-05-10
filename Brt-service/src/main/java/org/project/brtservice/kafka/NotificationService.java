package org.project.brtservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.brtservice.requests.BrtRequest;
import org.project.brtservice.requests.CdrRequest;
import org.project.brtservice.requests.HrsRequest;
import org.project.brtservice.requests.MonthRequest;
import org.project.brtservice.service.BrtService;
import org.springframework.stereotype.Service;

/**
 * NotificationService представляет сервис для обработки уведомлений, связанных с системой BRT (Billing and Rating).
 * Он получает уведомления в виде различных типов запросов (CdrRequest, HrsRequest, MonthRequest),
 * обрабатывает их и отправляет соответствующие уведомления через QueueProducer.
 * Метод processNotification(CdrRequest) выполняет авторизацию пользователя и отправляет уведомление,
 * если авторизация успешна. Методы processNotification(HrsRequest) и processNotification(MonthRequest)
 * выполняют операции с балансом пользователя или ежемесячными списаниями в зависимости от полученного запроса.
 * Метод sendNotification(BrtRequest) отправляет уведомления о событиях в системе BRT через QueueProducer.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final BrtService brtService;
    private final QueueProducer queueProducer;

    public void processNotification(CdrRequest notification) {
        log.info(String.valueOf(notification));
        var authorizedUser = brtService.authorize(notification);
        authorizedUser.ifPresent(this::sendNotification);
    }
    public void processNotification(HrsRequest notification) {
        log.error("prcess hrs");
        log.info(String.valueOf(notification));
        if (notification.debit()>0) {
            brtService.balanceOperation(notification);
        }
    }
    public void processNotification(MonthRequest notification) {
        log.info(String.valueOf(notification));
        brtService.monthBalanceOperation(notification);
    }
    public void sendNotification(BrtRequest notification) {
        log.info("btr service");
        queueProducer.sendMessage(notification);
    }
}

