package com.labresults.orderservice.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @GetMapping("/customer/{customerId}")
    CustomerResponse getUser(@PathVariable("customerId") UUID customerId);
}
