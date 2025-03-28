package com.labresults.orderservice.order;

import com.labresults.orderservice.customer.CustomerClient;
import com.labresults.orderservice.customer.CustomerResponse;
import com.labresults.orderservice.exception.EntityNotFoundException;
import com.labresults.orderservice.order.model.Order;
import com.labresults.orderservice.order.model.dto.OrderStatusDTO;
import com.labresults.orderservice.order.model.enums.OrderStatus;
import com.labresults.orderservice.order.model.request.NotificationRequest;
import com.labresults.orderservice.order.model.request.OpenOrderRequest;
import com.labresults.orderservice.order.model.dto.OrderDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final String SORT_PROPERTIES = "createdAt";
    private static final String EXCHANGE_NAME = "notification.exchange";
    private static final String ROUTING_KEY = "notification.send";
    private static final String NOTIFICATION_SUBJECT = "LabResults: Order status %s";
    private static final String NOTIFICATION_MESSAGE = "Order ID: %s, status: %s, date: %s";

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    private final RabbitTemplate rabbitTemplate;

    private final CustomerClient customerClient;

    public OrderDTO createOrder(OpenOrderRequest request) {
        UUID customerId = request.getCustomerId();

        CustomerResponse customer;
        try {
            customer = customerClient.getUser(customerId);
        } catch (FeignException.NotFound e) {
            throw new EntityNotFoundException(customerId.toString());
        }

        Order order = Order.builder()
                .customerId(customer.getId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .build();

        Order savedOrder = orderRepository.save(order);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .mail(customer.getEmail())
                .subject(NOTIFICATION_SUBJECT.formatted(savedOrder.getStatus()))
                .message(NOTIFICATION_MESSAGE.formatted(savedOrder.getId(), savedOrder.getStatus(), savedOrder.getCreatedAt()))
                .build();

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, notificationRequest);

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public void deleteOrderById(UUID orderId) {
        orderRepository.deleteById(orderId);
    }

    public OrderDTO getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(orderId.toString()));

        return modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> getAllOrders(Integer page, Integer size, Sort.Direction sort) {
        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size >= 1) ? size : 10;
        Sort.Direction sortDirection = (sort != null) ? sort : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, SORT_PROPERTIES));

        Page<Order> ordersPage = orderRepository.findAll(pageable);

        return ordersPage
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderStatusDTO getOrderStatus(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(orderId.toString()));

        return OrderStatusDTO.builder()
                .id(order.getId())
                .status(order.getStatus())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public OrderStatusDTO updateOrderStatus(UUID orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(orderId.toString()));

        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        Order updatedOrder = orderRepository.save(order);

        CustomerResponse customer;
        try {
            customer = customerClient.getUser(updatedOrder.getCustomerId());
        } catch (FeignException.NotFound e) {
            throw new EntityNotFoundException(updatedOrder.getCustomerId().toString());
        }

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .mail(customer.getEmail())
                .subject(NOTIFICATION_SUBJECT.formatted(updatedOrder.getStatus()))
                .message(NOTIFICATION_MESSAGE.formatted(updatedOrder.getId(), updatedOrder.getStatus(), updatedOrder.getUpdatedAt()))
                .build();

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, notificationRequest);

        return OrderStatusDTO.builder()
                .id(updatedOrder.getId())
                .status(updatedOrder.getStatus())
                .updatedAt(updatedOrder.getUpdatedAt())
                .build();
    }
}
