package com.labresults.authservice.auth;

import com.labresults.authservice.auth.request.LoginRequest;
import com.labresults.authservice.auth.request.RegisterRequest;
import com.labresults.authservice.auth.response.LoginResponse;
import com.labresults.authservice.auth.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    public RegisterResponse registerUser(RegisterRequest request) {
        // TO-DO: RabbitMQ

        RegisterResponse response = RegisterResponse.builder()
                .test("RESPONSE TO-DO")
                .test2("RESPONSE TO-DO")
                .build();

        return response;
    }

    public LoginResponse loginUser(LoginRequest request) {
        // TO-DO

        LoginResponse response = LoginResponse.builder()
                .test("RESPONSE TO-DO")
                .test2("RESPONSE TO-DO")
                .build();

        return response;
    }
}
