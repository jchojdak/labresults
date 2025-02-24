package com.labresults.notificationservice.rabbit;

import com.labresults.notificationservice.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final MailService mailService;

    @RabbitListener(queues = "notification.queue")
    public void receiveMessage(String message) {
        // TO-DO String to, String subject, String text
        mailService.send("implement.me@gmail.com", "LabResults - notification", message);
    }
}
