package com.labresults.sampleservice.sample;

import com.labresults.sampleservice.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class SampleServiceTest {

    private static final String SAMPLE_NAME = "Sample Name";
    private static final String SAMPLE_TYPE = "Sample Type";

    @InjectMocks
    private SampleService sampleService;

    @Mock
    private SampleRepository sampleRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void saveSample_savesAndReturnsSample() {
        CreateSampleRequest request = new CreateSampleRequest(UUID.randomUUID(), SAMPLE_NAME, SAMPLE_TYPE);
        Sample sample = Sample.builder()
                .orderId(request.getOrderId())
                .name(request.getName())
                .type(request.getType())
                .build();
        when(sampleRepository.save(any(Sample.class))).thenReturn(sample);

        Sample result = sampleService.saveSample(request);

        assertNotNull(result);
        assertEquals(request.getOrderId(), result.getOrderId());
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getType(), result.getType());
        verify(sampleRepository, times(1)).save(any(Sample.class));
    }

    @Test
    void deleteSampleById_deletesSample() {
        UUID sampleId = UUID.randomUUID();

        sampleService.deleteSampleById(sampleId);

        verify(sampleRepository, times(1)).deleteById(sampleId);
    }

    @Test
    void findSampleById_returnsSample() {
        UUID sampleId = UUID.randomUUID();
        Sample sample = new Sample();
        when(sampleRepository.findById(sampleId)).thenReturn(Optional.of(sample));

        Sample result = sampleService.findSampleById(sampleId);

        assertNotNull(result);
        assertEquals(sample, result);
        verify(sampleRepository, times(1)).findById(sampleId);
    }

    @Test
    void findSampleById_throwsEntityNotFoundException() {
        UUID sampleId = UUID.randomUUID();
        when(sampleRepository.findById(sampleId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sampleService.findSampleById(sampleId));
        verify(sampleRepository, times(1)).findById(sampleId);
    }

    @Test
    void findSamplesByOrderId_returnsSamples() {
        UUID orderId = UUID.randomUUID();
        List<Sample> samples = List.of(new Sample(), new Sample());
        when(sampleRepository.findByOrderId(orderId)).thenReturn(samples);

        List<Sample> result = sampleService.findSamplesByOrderId(orderId);

        assertNotNull(result);
        assertEquals(samples.size(), result.size());
        verify(sampleRepository, times(1)).findByOrderId(orderId);
    }
}
