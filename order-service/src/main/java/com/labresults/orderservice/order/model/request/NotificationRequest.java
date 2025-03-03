package com.labresults.orderservice.order.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequest {
    String mail;
    String subject;
    String message;
}
