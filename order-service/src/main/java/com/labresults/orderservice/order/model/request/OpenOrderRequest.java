package com.labresults.orderservice.order.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OpenOrderRequest {
    @NotNull(message = "Customer ID cannot be null")
    private UUID customerId;
}
