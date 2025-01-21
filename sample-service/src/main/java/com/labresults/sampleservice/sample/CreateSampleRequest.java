package com.labresults.sampleservice.sample;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateSampleRequest {
    private UUID orderId;
    private String name;
    private String type;
}
