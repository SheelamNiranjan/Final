package com.example.telemedicine_backend.repository;

import com.example.telemedicine_backend.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}