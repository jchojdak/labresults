package com.labresults.receptionservice.reception.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateSampleDTO {
    private UUID orderId;
    private String name;
    private String type;
}
