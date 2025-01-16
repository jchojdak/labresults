package com.labresults.orderservice.order.model.request;

import com.labresults.orderservice.order.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OpenOrderRequest {
    @NotNull(message = "Customer ID cannot be null")
    private UUID customerId;

    @NotNull(message = "Order status cannot be null")
    private OrderStatus status;

    private List<CreateSampleRequest> samples;
}
