package com.labresults.receptionservice.reception;

import com.labresults.receptionservice.reception.dto.CreateCustomerDTO;
import com.labresults.receptionservice.reception.dto.CreateOrderDTO;
import com.labresults.receptionservice.reception.dto.CreateSampleDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reception")
@RequiredArgsConstructor
public class ReceptionController {
    private final ReceptionService receptionService;

    @GetMapping("/test")
    public String test() {
        return "OK: RECEPTION-SERVICE";
    }

    // TO-DO: add response

    @PostMapping("/customer")
    // TO-DO: add security for staff and admin
    public String createCustomer(@RequestBody @Valid CreateCustomerDTO request) {
        return receptionService.createCustomer(request);
    }

    @PostMapping("/sample")
    // TO-DO: add security for staff and admin
    public String createSample(@RequestBody @Valid CreateSampleDTO request) {
        return receptionService.createSample(request);
    }

    @PostMapping("/order")
    // TO-DO: add security for staff and admin
    public String createOrder(@RequestBody @Valid CreateOrderDTO request) {
        return receptionService.createOrder(request);
    }

    @DeleteMapping("/customer/{customerId}")
    // TO-DO: add security for staff and admin
    public String deleteCustomerById(@PathVariable UUID customerId) {
        return receptionService.deleteCustomerById(customerId);
    }

    @DeleteMapping("/sample/{sampleId}")
    // TO-DO: add security for staff and admin
    public String deleteSampleById(@PathVariable UUID sampleId) {
        return receptionService.deleteSampleById(sampleId);
    }

    @DeleteMapping("/order/{orderId}")
    // TO-DO: add security for staff and admin
    public String deleteOrderById(@PathVariable UUID orderId) {
        return receptionService.deleteOrderById(orderId);
    }
}
