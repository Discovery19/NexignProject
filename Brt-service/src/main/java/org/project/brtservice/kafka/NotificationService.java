package org.project.brtservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.brtservice.requests.BrtRequest;
import org.project.brtservice.requests.CdrRequest;
import org.project.brtservice.requests.HrsRequest;
import org.project.brtservice.requests.MonthRequest;
import org.project.brtservice.service.BrtService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final BrtService brtService;
    private final QueueProducer queueProducer;

    public void processNotification(CdrRequest notification) {
        log.info(String.valueOf(notification));
        var authorizedUser = brtService.authorize(notification);
        log.info("user "+authorizedUser);
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

