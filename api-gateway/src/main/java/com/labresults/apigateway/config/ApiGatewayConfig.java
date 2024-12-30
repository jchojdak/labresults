package com.labresults.apigateway.config;

import com.labresults.apigateway.security.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiGatewayConfig {
    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb:http://auth-service"))
                .route("user-service", r -> r.path("/api/v1/users/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb:http://user-service"))
                .build();
    }
}
