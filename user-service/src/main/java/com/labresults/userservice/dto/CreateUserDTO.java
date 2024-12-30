package com.labresults.userservice.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateUserDTO {
    private String email;
    private String pesel;
    private String firstName;
    private String lastName;
    private String mobile;
    private LocalDate dateOfBirth;
}
