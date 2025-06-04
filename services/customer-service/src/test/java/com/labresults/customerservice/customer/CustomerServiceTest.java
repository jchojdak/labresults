package com.labresults.customerservice.customer;

import com.labresults.customerservice.customer.dto.CreateCustomerDTO;
import com.labresults.customerservice.customer.dto.CustomerDTO;
import com.labresults.customerservice.exception.EntityAlreadyExistsException;
import com.labresults.customerservice.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private static final String TEST_MAIL = "test@example.com";

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerService customerService;

    private CreateCustomerDTO createCustomerDTO;
    private Customer customer;
    private CustomerDTO customerDTO;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        createCustomerDTO = new CreateCustomerDTO();
        createCustomerDTO.setEmail(TEST_MAIL);
        customer = new Customer();
        customer.setId(customerId);
        customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);
    }

    @Test
    void createCustomerSuccessfully() {
        when(customerRepository.existsByEmail(createCustomerDTO.getEmail())).thenReturn(false);
        when(modelMapper.map(createCustomerDTO, Customer.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO result = customerService.createCustomer(createCustomerDTO);

        assertNotNull(result);
        assertEquals(customerId, result.getId());
        verify(customerRepository).save(customer);
    }

    @Test
    void createCustomerThrowsEntityAlreadyExistsException() {
        when(customerRepository.existsByEmail(createCustomerDTO.getEmail())).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> customerService.createCustomer(createCustomerDTO));
    }

    @Test
    void deleteCustomerByIdSuccessfully() {
        doNothing().when(customerRepository).deleteById(customerId);

        customerService.deleteCustomerById(customerId);

        verify(customerRepository).deleteById(customerId);
    }

    @Test
    void getCustomerByIdSuccessfully() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals(customerId, result.getId());
    }

    @Test
    void getCustomerByIdThrowsEntityNotFoundException() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(customerId));
    }

    @Test
    void getAllCustomersSuccessfully() {
        Page<Customer> page = new PageImpl<>(Collections.singletonList(customer));
        when(customerRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        List<CustomerDTO> result = customerService.getAllCustomers(0, 10, Sort.Direction.ASC);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(customerId, result.get(0).getId());
    }
}
