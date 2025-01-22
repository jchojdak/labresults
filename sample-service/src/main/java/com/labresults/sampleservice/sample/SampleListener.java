package com.labresults.sampleservice.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SampleListener {
    private final SampleService sampleService;

    @RabbitListener(queues = "sample.create.queue")
    public void handleCreateSample(CreateSampleRequest request) {
        sampleService.saveSample(request);
    }

    @RabbitListener(queues = "sample.delete.queue")
    public void handleDeleteSample(UUID sampleId) {
        sampleService.deleteSampleById(sampleId);
    }
}
