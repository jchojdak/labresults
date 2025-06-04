package com.labresults.resultservice.result;

import com.labresults.resultservice.exception.EntityNotFoundException;
import com.labresults.resultservice.result.dto.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
    private final ModelMapper modelMapper;

    public List<ResultDTO> getResultsByOrderIdAndPesel(UUID orderId, String pesel) {
        List<Result> results = resultRepository.findByOrderIdAndCustomerPesel(orderId, pesel);
        if (results == null || results.isEmpty()) {
            throw new EntityNotFoundException(orderId.toString());
        }

        return results.stream()
                .map(result -> modelMapper.map(result, ResultDTO.class))
                .collect(Collectors.toList());
    }

}
