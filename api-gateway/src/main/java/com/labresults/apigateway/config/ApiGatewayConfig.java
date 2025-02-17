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
                .route("auth-service", r -> r.path("/auth/**")
                        .uri("lb://auth-service"))
                .route("customer-service", r -> r.path("/customer/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb://customer-service"))
                .route("order-service", r -> r.path("/order/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb://order-service"))
                .route("sample-service", r -> r.path("/sample/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb://sample-service"))
                .build();
    }
}
