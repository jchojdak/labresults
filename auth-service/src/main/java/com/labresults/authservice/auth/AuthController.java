package com.labresults.authservice.auth;

import com.labresults.authservice.auth.request.LoginRequest;
import com.labresults.authservice.auth.request.RegisterRequest;
import com.labresults.authservice.auth.response.LoginResponse;
import com.labresults.authservice.auth.response.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/test")
    public String test() {
        return "OK: AUTH-SERVICE";
    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody @Valid RegisterRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse registerUser(@RequestBody @Valid LoginRequest request) {
        return authService.loginUser(request);
    }
}
