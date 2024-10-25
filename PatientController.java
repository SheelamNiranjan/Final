package com.example.telemedicine_backend.controller;

import com.example.telemedicine_backend.model.Patient;
import com.example.telemedicine_backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.findAllPatients();
    }
    @PostMapping
    public Patient registerPatient(@RequestBody Patient patient) {
        return patientService.registerPatient(patient);
    }

    @GetMapping("/{email}")
    public Patient getPatient(@PathVariable String email) {
        return patientService.findByEmail(email);
    }
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return patientService.updatePatient(id, patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<Patient> patient = patientService.login(email, password);
        if (patient.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Please check your credentials.");
        }
    }
}