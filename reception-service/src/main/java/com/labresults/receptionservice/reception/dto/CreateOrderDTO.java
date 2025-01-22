package com.labresults.receptionservice.reception.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderDTO {
    @NotNull(message = "Customer ID cannot be null")
    private UUID customerId;
}
