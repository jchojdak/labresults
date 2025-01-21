package com.labresults.customerservice.customer;

import com.labresults.customerservice.customer.dto.CreateCustomerDTO;
import com.labresults.customerservice.customer.dto.CustomerDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/test")
    public String test() {
        return "OK: CUSTOMER-SERVICE";
    }

/*    @PostMapping
    // TO-DO: add security for personel and admin
    public CustomerDTO createCustomer(@RequestBody @Valid CreateCustomerDTO request) {
        return customerService.createCustomer(request);
    }

    @GetMapping("/{customerId}")
    // TO-DO: add security for personel and admin
    public CustomerDTO getUser(@PathVariable UUID customerId) {
        return customerService.getCustomerById(customerId);
    }

    @GetMapping
    // TO-DO: add security for personel and admin
    public List<CustomerDTO> getAllCustomers(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer size,
                                             @RequestParam(required = false) Sort.Direction sort) {
        return customerService.getAllCustomers(page, size, sort);
    }*/

}
