package com.labresults.receptionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReceptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReceptionServiceApplication.class, args);
    }

}