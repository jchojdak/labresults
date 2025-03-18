package com.labresults.apigateway.config;

import com.labresults.apigateway.security.JwtConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtConverter jwtConverter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.GET,"/swagger-ui.html", "/webjars/swagger-ui/*", "/v3/api-docs/swagger-config", "/actuator/*").permitAll()
                        .pathMatchers(HttpMethod.GET, "/sample/test", "/order/test", "/customer/test", "/result/test").permitAll()
                        .pathMatchers(HttpMethod.GET, "/sample/v3/api-docs", "/order/v3/api-docs", "/customer/v3/api-docs", "/result/v3/api-docs").permitAll()
                        .pathMatchers(HttpMethod.POST, "/sample").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .pathMatchers(HttpMethod.DELETE, "/sample/*").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/sample/*").hasAnyRole("ADMIN", "RECEPTIONIST", "LAB_TECHNICIAN")
                        .pathMatchers(HttpMethod.GET, "/sample/order/*").hasAnyRole("ADMIN", "RECEPTIONIST", "LAB_TECHNICIAN")
                        .pathMatchers(HttpMethod.POST, "/order/open").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .pathMatchers(HttpMethod.GET, "/order/*").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .pathMatchers(HttpMethod.GET, "/order").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .pathMatchers(HttpMethod.POST, "/customer").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .pathMatchers(HttpMethod.GET, "/customer/*").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .pathMatchers(HttpMethod.GET, "/customer").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .pathMatchers(HttpMethod.GET, "/result/*/collect").permitAll()
                        .anyExchange().authenticated()
                )
                //.oauth2Login(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));
                //.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

}
