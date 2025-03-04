package com.labresults.resultservice.result;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="results")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "sample_id", nullable = false)
    private UUID sampleId;

    @Column(name = "sample_name", nullable = false)
    private String sampleName;

    @Column(name = "sample_type", nullable = false)
    private String sampleType;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "customer_pesel")
    private String customerPesel;

    @Column(name = "customer_date_of_birth")
    private LocalDate customerDateOfBirth;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "employee_comment")
    private String employeeComment;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultParameter> parameters;
}
