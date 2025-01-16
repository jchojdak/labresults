package com.labresults.orderservice.order;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="orders")
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Id
    @Column(name = "id", nullable = false)
    private UUID customerId;

    // TO-DO
}
