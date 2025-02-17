package com.labresults.sampleservice.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleController {
    private final SampleService sampleService;

    @GetMapping("/test")
    public String test() {
        return "OK: SAMPLE-SERVICE";
    }

    @PostMapping
    public Sample createSample(@RequestBody @Validated CreateSampleRequest request) {
        return sampleService.saveSample(request);
    }

    @DeleteMapping("/{sampleId}")
    public String deleteSample(@PathVariable UUID sampleId) {
        sampleService.deleteSampleById(sampleId);
        return "Sample with ID=%s has been deleted.".formatted(sampleId);
    }

    @GetMapping("/{sampleId}")
    public Sample findSampleById(@PathVariable UUID sampleId) {
        return sampleService.findSampleById(sampleId);
    }

    @GetMapping("/order/{orderId}")
    public List<Sample> findSamplesByOrderId(@PathVariable UUID orderId) {
        return sampleService.findSamplesByOrderId(orderId);
    }
}
