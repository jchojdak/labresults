package com.labresults.sampleservice.sample;

import com.labresults.sampleservice.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Sample findSampleById(UUID sampleId) {
        Sample sample = sampleRepository.findById(sampleId)
                .orElseThrow(() -> new EntityNotFoundException(sampleId.toString()));

        return sample;
    }

    public List<Sample> findSamplesByOrderId(UUID orderId) {
        return sampleRepository.findByOrderId(orderId);
    }
}
