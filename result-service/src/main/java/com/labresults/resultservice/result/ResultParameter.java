package com.labresults.resultservice.result;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "result_parameters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "unit")
    private String unit;

    @Column(name = "reference_range")
    private String referenceRange;
}
