package com.labresults.userservice.user;

import com.labresults.userservice.user.dto.CreateUserDTO;
import com.labresults.userservice.user.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/test")
    public String test() {
        return "OK: USER-SERVICE";
    }

    @PostMapping
    // TO-DO: add security for personel and admin
    public UserDTO createUser(@RequestBody @Valid CreateUserDTO request) {
        return userService.createUser(request);
    }

    @GetMapping("/{userId}")
    // TO-DO: add security for personel and admin
    public UserDTO getUser(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    // TO-DO: add security for personel and admin
    public List<UserDTO> getAllUsers(@RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer size,
                                     @RequestParam(required = false) Sort.Direction sort) {
        return userService.getAllUsers(page, size, sort);
    }

}
