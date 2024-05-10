package org.project.cdrservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.cdrservice.config.ApplicationConfig;
import org.springframework.stereotype.Service;

/**
 * NotificationService представляет сервис для отправки уведомлений через очередь сообщений.
 * Он использует QueueProducer для отправки уведомлений, полученных в виде объектов CdrRequest.
 * Данный сервис является частью интеграционного слоя приложения и обеспечивает передачу данных между
 * различными компонентами через очередь сообщений.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final QueueProducer queueProducer;

    public void sendNotification(CdrRequest notification) {
        queueProducer.sendMessage(notification);
    }
}