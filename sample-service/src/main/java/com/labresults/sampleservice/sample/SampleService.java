package com.labresults.sampleservice.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository sampleRepository;

    @RabbitListener(queues = "sample.create.queue")
    public Sample createSample(CreateSampleRequest request) {
        Sample sample = Sample.builder()
                .orderId(request.getOrderId())
                .name(request.getName())
                .type(request.getType())
                .build();

        // SAVE SAMPLE
        Sample savedSample = sampleRepository.save(sample);

        return savedSample;
    }

    @RabbitListener(queues = "sample.delete.queue")
    public void deleteSampleById(UUID sampleId) {
        // DELETE SAMPLE
        sampleRepository.deleteById(sampleId);
    }
}
