package com.labresults.receptionservice.reception;

import com.labresults.receptionservice.reception.dto.CreateCustomerDTO;
import com.labresults.receptionservice.reception.dto.CreateOrderDTO;
import com.labresults.receptionservice.reception.dto.CreateSampleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceptionService {
    private final RabbitTemplate rabbitTemplate;

    public String createCustomer(CreateCustomerDTO request) {
        // SEND TO CUSTOMER SERVICE
        rabbitTemplate.convertAndSend("customer.exchange", "customer.create", request);

        return "NICE 1";
    }

    public String createSample(CreateSampleDTO request) {
        // SEND TO SAMPLE SERVICE
        rabbitTemplate.convertAndSend("sample.exchange", "sample.create", request);

        return "NICE 2";
    }

    public String deleteCustomerById(UUID customerId) {
        // SEND TO CUSTOMER SERVICE
        rabbitTemplate.convertAndSend("customer.exchange", "customer.delete", customerId);

        return "NICE 3";
    }

    public String deleteSampleById(UUID sampleId) {
        // SEND TO SAMPLE SERVICE
        rabbitTemplate.convertAndSend("sample.exchange", "sample.delete", sampleId);

        return "NICE 4";
    }

    public String createOrder(CreateOrderDTO request) {
        // SEND TO ORDER SERVICE
        rabbitTemplate.convertAndSend("order.exchange", "order.create", request);

        return "NICE 5";
    }

    public String deleteOrderById(UUID orderId) {
        // SEND TO ORDER SERVICE
        rabbitTemplate.convertAndSend("order.exchange", "order.delete", orderId);

        return "NICE 6";
    }
}
