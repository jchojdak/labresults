package com.labresults.notificationservice.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService service;

    @RabbitListener(queues = "notification.queue")
    public void receiveMessage(String message) {
        service.sendEmail(message);
    }
}
