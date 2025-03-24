package com.labresults.orderservice.order;

import com.labresults.orderservice.customer.CustomerClient;
import com.labresults.orderservice.customer.CustomerResponse;
import com.labresults.orderservice.exception.EntityNotFoundException;
import com.labresults.orderservice.order.model.Order;
import com.labresults.orderservice.order.model.dto.OrderDTO;
import com.labresults.orderservice.order.model.dto.OrderStatusDTO;
import com.labresults.orderservice.order.model.enums.OrderStatus;
import com.labresults.orderservice.order.model.request.NotificationRequest;
import com.labresults.orderservice.order.model.request.OpenOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private static final String CUSTOMER_EMAIL = "test@example.com";
    private static final String CUSTOMER_PESEL = "12345678901";
    private static final String CUSTOMER_FIRST_NAME = "John";
    private static final String CUSTOMER_LAST_NAME = "Doe";
    private static final String CUSTOMER_MOBILE = "123456789";

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private CustomerClient customerClient;

    @InjectMocks
    private OrderService orderService;

    private UUID customerId;
    private UUID orderId;
    private CustomerResponse customerResponse;
    private Order order;
    private OrderDTO orderDTO;
    private OpenOrderRequest openOrderRequest;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        orderId = UUID.randomUUID();
        customerResponse = CustomerResponse.builder()
                .id(customerId)
                .email(CUSTOMER_EMAIL)
                .pesel(CUSTOMER_PESEL)
                .firstName(CUSTOMER_FIRST_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .mobile(CUSTOMER_MOBILE)
                .build();
        order = Order.builder()
                .id(orderId)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .build();
        orderDTO = new OrderDTO(orderId, customerId, LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED);
        openOrderRequest = new OpenOrderRequest(customerId);
    }

    @Test
    void createOrderSuccessfully() {
        when(customerClient.getUser(customerId)).thenReturn(customerResponse);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(modelMapper.map(any(Order.class), eq(OrderDTO.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.createOrder(openOrderRequest);

        assertNotNull(result);
        assertEquals(orderDTO, result);
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), any(NotificationRequest.class));
    }

    @Test
    void deleteOrderByIdSuccessfully() {
        doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteOrderById(orderId);

        verify(orderRepository).deleteById(orderId);
    }

    @Test
    void getOrderByIdSuccessfully() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO);

        OrderDTO result = orderService.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderDTO, result);
    }

    @Test
    void getOrderByIdNotFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(orderId));
    }

    @Test
    void getAllOrdersSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Order> ordersPage = new PageImpl<>(Collections.singletonList(order));
        when(orderRepository.findAll(pageable)).thenReturn(ordersPage);
        when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO);

        List<OrderDTO> result = orderService.getAllOrders(0, 10, Sort.Direction.ASC);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderDTO, result.get(0));
    }

    @Test
    void getOrderStatusSuccessfully() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderStatusDTO result = orderService.getOrderStatus(orderId);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getStatus(), result.getStatus());
        assertEquals(order.getUpdatedAt(), result.getUpdatedAt());
    }

    @Test
    void getOrderStatusNotFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.getOrderStatus(orderId));
    }

    @Test
    void updateOrderStatusSuccessfully() {
        OrderStatus newStatus = OrderStatus.COMPLETED;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderStatusDTO result = orderService.updateOrderStatus(orderId, newStatus);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(newStatus, result.getStatus());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    void updateOrderStatusNotFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.updateOrderStatus(orderId, OrderStatus.COMPLETED));
    }
}
