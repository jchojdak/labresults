package com.labresults.customerservice.customer;

import com.labresults.customerservice.customer.dto.CreateCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerListener {
    private final CustomerService customerService;

    @RabbitListener(queues = "customer.create.queue")
    public void handleCreateCustomer(CreateCustomerDTO createRequest) {
        customerService.createCustomer(createRequest);
    }

    @RabbitListener(queues = "customer.delete.queue")
    public void handleDeleteCustomer(UUID customerId) {
        customerService.deleteCustomerById(customerId);
    }
}
