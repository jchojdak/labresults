package com.labresults.sampleservice.sample;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateSampleRequest {
    @NotNull(message = "Order ID cannot be null")
    private UUID orderId;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Type cannot be blank")
    private String type;
}
