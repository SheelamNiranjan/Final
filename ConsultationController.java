package com.example.telemedicine_backend.controller;

import com.example.telemedicine_backend.model.Consultation;
import com.example.telemedicine_backend.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @PostMapping
    public Consultation createConsultation(@RequestBody Consultation consultation) {
        return  consultationService.createConsultation(consultation);

    }
    // Get all consultations
    @GetMapping
    public List<Consultation> getAllConsultations() {
        return consultationService.getAllConsultations();
    }

    // Get a consultation by ID
    @GetMapping("/{id}")
    public Consultation getConsultationById(@PathVariable Long id) {
        return consultationService.findConsultationById(id);
    }

    // Update a consultation
    @PutMapping("/{id}")
    public Consultation updateConsultation(@PathVariable Long id, @RequestBody Consultation consultation) {
        return consultationService.updateConsultation(id, consultation);
    }

    // Delete a consultation
    @DeleteMapping("/{id}")
    public void deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultation(id);
}

}
