package com.example.telemedicine_backend.service;


import com.example.telemedicine_backend.model.Doctor;
import com.example.telemedicine_backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    // Create a new doctor
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Find a doctor by ID
    public Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null); // Return null or handle not found
    }

    // Update a doctor
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Doctor existingDoctor = doctorRepository.findById(id).orElse(null);
        if (existingDoctor != null) {
            existingDoctor.setName(updatedDoctor.getName());
            existingDoctor.setSpecialty(updatedDoctor.getSpecialty());
            return doctorRepository.save(existingDoctor);
        }
        return null; // or throw an exception for not found
    }
    // Delete a doctor
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
}
}