package com.labresults.orderservice.order.model.dto;

import com.labresults.orderservice.order.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {
    private UUID id;
    private OrderStatus status;
    private LocalDateTime updatedAt;
}
