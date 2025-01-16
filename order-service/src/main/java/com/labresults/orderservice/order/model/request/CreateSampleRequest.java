package com.labresults.orderservice.order.model.request;

import lombok.Data;

@Data
public class CreateSampleRequest {
    private String name;
    private String type;
}
