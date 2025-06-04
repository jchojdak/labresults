package com.labresults.resultservice.result.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultParameterDTO {
    private UUID id;
    private String name;
    private String value;
    private String unit;
    private String referenceRange;
}
