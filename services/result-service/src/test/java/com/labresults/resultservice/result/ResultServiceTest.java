package com.labresults.resultservice.result;

import com.labresults.resultservice.exception.EntityNotFoundException;
import com.labresults.resultservice.result.dto.ResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultServiceTest {

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ResultService resultService;

    private Result result;
    private ResultDTO resultDTO;
    private UUID orderId;
    private String pesel;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        pesel = "12345678901";

        result = new Result();
        resultDTO = new ResultDTO();
    }

    @Test
    void getResultsByOrderIdAndPesel_returnsResults() {
        when(resultRepository.findByOrderIdAndCustomerPesel(orderId, pesel)).thenReturn(List.of(result));
        when(modelMapper.map(result, ResultDTO.class)).thenReturn(resultDTO);

        List<ResultDTO> results = resultService.getResultsByOrderIdAndPesel(orderId, pesel);

        assertEquals(1, results.size());
        assertEquals(resultDTO, results.get(0));
    }

    @Test
    void getResultsByOrderIdAndPesel_throwsEntityNotFoundException_whenNoResults() {
        when(resultRepository.findByOrderIdAndCustomerPesel(orderId, pesel)).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> resultService.getResultsByOrderIdAndPesel(orderId, pesel));
    }

    @Test
    void getResultsByOrderIdAndPesel_throwsEntityNotFoundException_whenResultsIsNull() {
        when(resultRepository.findByOrderIdAndCustomerPesel(orderId, pesel)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> resultService.getResultsByOrderIdAndPesel(orderId, pesel));
    }

}
