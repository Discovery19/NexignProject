package org.project.cdrservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.cdrservice.config.ApplicationConfig;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final QueueProducer queueProducer;

    public void sendNotification(CdrRequest notification) {
        queueProducer.sendMessage(notification);
    }
}