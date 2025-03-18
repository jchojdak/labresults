package com.labresults.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiGatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("result-service", r -> r.path("/result/**")
                        //.filters(GatewayFilterSpec::tokenRelay)
                        .uri("lb://result-service"))
                .route("customer-service", r -> r.path("/customer/**")
                        //.filters(GatewayFilterSpec::tokenRelay)
                        .uri("lb://customer-service"))
                .route("order-service", r -> r.path("/order/**")
                        //.filters(GatewayFilterSpec::tokenRelay)
                        .uri("lb://order-service"))
                .route("sample-service", r -> r.path("/sample/**")
                        //.filters(GatewayFilterSpec::tokenRelay)
                        .uri("lb://sample-service"))
                .build();
    }
}
