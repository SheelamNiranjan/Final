package com.example.telemedicine_backend.service;

import com.example.telemedicine_backend.model.Consultation;
import com.example.telemedicine_backend.model.Patient;
import com.example.telemedicine_backend.model.Doctor;
import com.example.telemedicine_backend.repository.ConsultationRepository;
import com.example.telemedicine_backend.repository.PatientRepository;
import com.example.telemedicine_backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Consultation createConsultation(Consultation consultation) {
        Patient patient = patientRepository.findById(consultation.getPatient().getId()).orElse(null);
        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }
        consultation.setPatient(patient);

        Doctor doctor = doctorRepository.findById(consultation.getDoctor().getId()).orElse(null);
        if (doctor == null) {
            throw new RuntimeException("Doctor not found");
        }
        consultation.setDoctor(doctor);

        return consultationRepository.save(consultation);
    }

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public Consultation findConsultationById(Long id) {
        return consultationRepository.findById(id).orElse(null);
    }

    public Consultation updateConsultation(Long id, Consultation updatedConsultation) {
        Consultation existingConsultation = consultationRepository.findById(id).orElse(null);
        if (existingConsultation != null) {
            existingConsultation.setPatient(updatedConsultation.getPatient());
            existingConsultation.setDoctor(updatedConsultation.getDoctor());
            existingConsultation.setScheduledDate(updatedConsultation.getScheduledDate());
            existingConsultation.setNotes(updatedConsultation.getNotes());
            return consultationRepository.save(existingConsultation);
        }
        return null;
    }
    public void deleteConsultation(Long id) {
        consultationRepository.deleteById(id);
    }
}

