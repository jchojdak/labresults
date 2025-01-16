package com.labresults.orderservice.order.model.dto;

import com.labresults.orderservice.order.model.enums.OrderStatus;
import com.labresults.orderservice.sample.Sample;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderDTO {
    private UUID id;
    private UUID customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus status;
    private List<Sample> samples;
}
