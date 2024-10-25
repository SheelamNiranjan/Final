package com.example.telemedicine_backend.repository;

import com.example.telemedicine_backend.model.Patient;
import com.example.telemedicine_backend.model.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDetailsRepository extends JpaRepository<PatientDetails, Long> {
    PatientDetails findByPatientId(Long patientId);

}
