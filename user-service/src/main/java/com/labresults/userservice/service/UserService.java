package com.labresults.userservice.service;

import com.labresults.userservice.dto.CreateUserDTO;
import com.labresults.userservice.dto.UserDTO;
import com.labresults.userservice.model.User;
import com.labresults.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO createUser(CreateUserDTO createRequest) {
        if (userRepository.existsByEmail(createRequest.getEmail())) {
            // TO-DO
            //throw new UserAlreadyExistsException();
        }

        User user = modelMapper.map(createRequest, User.class);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }
}
