package com.labresults.resultservice.result.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    private UUID id;
    private UUID orderId;
    private String sampleName;
    private String sampleType;
    private String customerPesel;
    private LocalDate customerDateOfBirth;
    private String employeeComment;
    private List<ResultParameterDTO> parameters;
}
