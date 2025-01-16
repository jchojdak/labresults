package com.labresults.orderservice.order;

import com.labresults.orderservice.exception.EntityNotFoundException;
import com.labresults.orderservice.order.model.Order;
import com.labresults.orderservice.order.model.enums.OrderStatus;
import com.labresults.orderservice.order.model.request.OpenOrderRequest;
import com.labresults.orderservice.order.model.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderDTO openOrder(OpenOrderRequest request) {
        UUID customerId = request.getCustomerId();
        /*
        CHECK CUSTOMER IN CUSTOMER-SERVICE
        if (customer.find(customerId).exists == false) {
            throw new EntityNotFoundException();
        }
         */

        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                //TO-DO: .samples()
                .build();

        Order savedOrder = orderRepository.save(order);
        // TO-DO: send samples to lab-service

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public OrderDTO getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(orderId.toString()));

        return modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> getAllCustomers(Integer page, Integer size, Sort.Direction sort) {
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
}
