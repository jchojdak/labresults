package com.labresults.userservice.service;

import com.labresults.userservice.dto.CreateUserDTO;
import com.labresults.userservice.dto.UserDTO;
import com.labresults.userservice.model.User;
import com.labresults.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserDTO createUser(CreateUserDTO createRequest) {


        User savedUser = repository.save();
        return toDTO(savedUser);
    }
}
