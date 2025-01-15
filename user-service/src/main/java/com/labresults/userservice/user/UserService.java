package com.labresults.userservice.user;

import com.labresults.userservice.exception.EntityAlreadyExistsException;
import com.labresults.userservice.exception.EntityNotFoundException;
import com.labresults.userservice.user.dto.CreateUserDTO;
import com.labresults.userservice.user.dto.UserDTO;
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
public class UserService {
    private static final String SORT_PROPERTIES = "createdAt";

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO createUser(CreateUserDTO createRequest) {
        if (userRepository.existsByEmail(createRequest.getEmail()))
            throw new EntityAlreadyExistsException(createRequest.getEmail());

        User user = modelMapper.map(createRequest, User.class);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> getAllUsers(Integer page, Integer size, Sort.Direction sort) {
        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size >= 1) ? size : 10;
        Sort.Direction sortDirection = (sort != null) ? sort : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, SORT_PROPERTIES));

        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
