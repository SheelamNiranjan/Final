package com.example.telemedicine_backend.controller;

import com.example.telemedicine_backend.model.PatientDetails;
import com.example.telemedicine_backend.service.PatientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/patient-details")
public class PatientDetailsController {

    private PatientDetailsService patientDetailsService;

    @Autowired
    public void setPatientDetailsService(PatientDetailsService patientDetailsService) {
        this.patientDetailsService = patientDetailsService;
    }

    @PostMapping
    public PatientDetails createPatientDetails(@RequestBody PatientDetails patientDetails) {
        return patientDetailsService.createPatientDetails(patientDetails);
    }

    @GetMapping
    public List<PatientDetails> getAllPatientDetails() {
        return patientDetailsService.getAllPatientDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetails> getPatientDetailsById(@PathVariable Long id) {
        PatientDetails details = patientDetailsService.getPatientDetailsById(id);
        return details != null ? ResponseEntity.ok(details) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDetails> updatePatientDetails(@PathVariable Long id, @RequestBody PatientDetails patientDetails) {
        PatientDetails updatedDetails = patientDetailsService.updatePatientDetails(id, patientDetails);
        return updatedDetails != null ? ResponseEntity.ok(updatedDetails) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientDetails(@PathVariable Long id) {
        patientDetailsService.deletePatientDetails(id);
        return ResponseEntity.noContent().build();
    }
}

