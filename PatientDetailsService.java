package com.example.telemedicine_backend.service;

import com.example.telemedicine_backend.model.Patient;
import com.example.telemedicine_backend.model.PatientDetails;
import com.example.telemedicine_backend.repository.PatientDetailsRepository;
import com.example.telemedicine_backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientDetailsService {

    private final PatientDetailsRepository patientDetailsRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public PatientDetailsService(PatientDetailsRepository patientDetailsRepository, PatientRepository patientRepository) {
        this.patientDetailsRepository = patientDetailsRepository;
        this.patientRepository = patientRepository;
    }

    public PatientDetails createPatientDetails(PatientDetails patientDetails) {
        Patient patient = patientRepository.findById(patientDetails.getPatient().getId()).orElse(null);
        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }
        patientDetails.setPatient(patient);
        return patientDetailsRepository.save(patientDetails);
    }

    public List<PatientDetails> getAllPatientDetails() {
        return patientDetailsRepository.findAll();
    }

    public PatientDetails getPatientDetailsById(Long id) {
        return patientDetailsRepository.findById(id).orElse(null);
    }

    public PatientDetails updatePatientDetails(Long id, PatientDetails updatedDetails) {
        return patientDetailsRepository.findById(id).map(existingDetails -> {
            existingDetails.setAddress(updatedDetails.getAddress());
            existingDetails.setPhoneNumber(updatedDetails.getPhoneNumber());
            existingDetails.setDateOfBirth(updatedDetails.getDateOfBirth());
            existingDetails.setGender(updatedDetails.getGender());
            return patientDetailsRepository.save(existingDetails);
        }).orElse(null);
    }

    public void deletePatientDetails(Long id) {
        patientDetailsRepository.deleteById(id);
    }

}


