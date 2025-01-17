package com.labresults.customerservice.customer.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CustomerDTO {
    private UUID id;
    private String email;
    private String pesel;
    private String firstName;
    private String lastName;
    private String mobile;
    private LocalDate dateOfBirth;
    private LocalDateTime registrationDate;
    private boolean active;
    private boolean blocked;
}
