package com.labresults.orderservice.order.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenOrderRequest {
    @NotNull(message = "Customer ID cannot be null")
    private UUID customerId;
}
