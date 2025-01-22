package com.labresults.sampleservice.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository sampleRepository;

    public Sample saveSample(CreateSampleRequest request) {
        Sample sample = Sample.builder()
                .orderId(request.getOrderId())
                .name(request.getName())
                .type(request.getType())
                .build();

        Sample savedSample = sampleRepository.save(sample);

        return savedSample;
    }

    public void deleteSampleById(UUID sampleId) {
        sampleRepository.deleteById(sampleId);
    }
}
