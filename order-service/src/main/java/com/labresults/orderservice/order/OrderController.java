package com.labresults.orderservice.order;

import com.labresults.orderservice.order.model.request.OpenOrderRequest;
import com.labresults.orderservice.order.model.dto.OrderDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/test")
    public String test() {
        return "OK: ORDER-SERVICE";
    }

    @PostMapping("/open")
    // TO-DO: add security for personel and admin
    public OrderDTO openOrder(@RequestBody @Valid OpenOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{orderId}")
    // TO-DO: add security for personel and admin
    public OrderDTO getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping
    // TO-DO: add security for personel and admin
    public List<OrderDTO> getAllOrders(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer size,
                                       @RequestParam(required = false) Sort.Direction sort) {
        return orderService.getAllCustomers(page, size, sort);
    }

}
