package com.labresults.customerservice.customer;

import com.labresults.customerservice.exception.EntityAlreadyExistsException;
import com.labresults.customerservice.exception.EntityNotFoundException;
import com.labresults.customerservice.customer.dto.CreateCustomerDTO;
import com.labresults.customerservice.customer.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final String SORT_PROPERTIES = "registrationDate";

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerDTO createCustomer(CreateCustomerDTO createRequest) {
        if (customerRepository.existsByEmail(createRequest.getEmail()))
            throw new EntityAlreadyExistsException(createRequest.getEmail());

        Customer customer = modelMapper.map(createRequest, Customer.class);

        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    public void deleteCustomerById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public CustomerDTO getCustomerById(UUID userId) {
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    public List<CustomerDTO> getAllCustomers(Integer page, Integer size, Sort.Direction sort) {
        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size >= 1) ? size : 10;
        Sort.Direction sortDirection = (sort != null) ? sort : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, SORT_PROPERTIES));

        Page<Customer> usersPage = customerRepository.findAll(pageable);

        return usersPage
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }
}
