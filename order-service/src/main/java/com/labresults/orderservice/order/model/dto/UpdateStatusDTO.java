package com.labresults.orderservice.order.model.dto;

import com.labresults.orderservice.order.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusDTO {
    private OrderStatus status;
}
