package com.example.telemedicine_backend.repository;

import com.example.telemedicine_backend.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}