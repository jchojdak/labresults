package com.labresults.notificationservice.notification;

import com.labresults.notificationservice.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final MailService mailService;

    @RabbitListener(queues = "notification.queue")
    public void receiveMessage(NotificationRequest request) {
        mailService.send(request.getMail(), request.getSubject(), request.getMessage());
    }
}
