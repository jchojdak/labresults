package com.labresults.orderservice.order;

import com.labresults.orderservice.order.model.request.OpenOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderListener {
    private final OrderService orderService;

    @RabbitListener(queues = "order.create.queue")
    public void handleCreateOrder(OpenOrderRequest request) {
        orderService.createOrder(request);
    }

    @RabbitListener(queues = "order.delete.queue")
    public void handleDeleteOrder(UUID orderId) {
        orderService.deleteOrderById(orderId);
    }
}
