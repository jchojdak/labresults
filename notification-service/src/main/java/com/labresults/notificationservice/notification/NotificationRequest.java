package com.labresults.notificationservice.notification;

import lombok.Data;

@Data
public class NotificationRequest {
    String mail;
    String subject;
    String message;
}
