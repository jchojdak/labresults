package com.labresults.sampleservice.sample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SampleRepository extends JpaRepository<Sample, UUID> {
}
