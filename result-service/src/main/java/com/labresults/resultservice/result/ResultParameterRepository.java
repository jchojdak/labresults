package com.labresults.resultservice.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResultParameterRepository extends JpaRepository<ResultParameter, UUID> {
}
