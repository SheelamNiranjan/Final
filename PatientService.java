package com.example.telemedicine_backend.service;


import com.example.telemedicine_backend.model.Patient;
import com.example.telemedicine_backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;



    public Patient registerPatient(Patient patient) {

        return patientRepository.save(patient);
    }

    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findById(id).orElse(null);
        if (existingPatient != null) {
            existingPatient.setName(updatedPatient.getName());
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setPassword(updatedPatient.getPassword()); // Ensure the password is hashed
            return patientRepository.save(existingPatient);
        }
        return null; // or throw an exception for not found
    }
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> login(String email, String password) {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByEmail(email));
        if (patient.isPresent() && patient.get().getPassword().equals(password)) {
            return patient; // Login successful
        }
        return Optional.empty(); // Login failed
    }

}