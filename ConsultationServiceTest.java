package com.example.telemedicine_backend.serviceTest;

import com.example.telemedicine_backend.model.Consultation;
import com.example.telemedicine_backend.model.Patient;
import com.example.telemedicine_backend.model.Doctor;
import com.example.telemedicine_backend.repository.ConsultationRepository;
import com.example.telemedicine_backend.repository.PatientRepository;
import com.example.telemedicine_backend.repository.DoctorRepository;
import com.example.telemedicine_backend.service.ConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ConsultationServiceTest {

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private ConsultationService consultationService;

    private Consultation consultation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consultation = new Consultation();
        consultation.setId(1L);
        consultation.setNotes("Follow-up visit");

        // Initialize Patient
        Patient patient = new Patient();
        patient.setId(1L);
        consultation.setPatient(patient);

        // Initialize Doctor
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        consultation.setDoctor(doctor);
    }
    @Test
    void testCreateConsultation() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(consultation.getPatient()));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(consultation.getDoctor()));
        when(consultationRepository.save(any(Consultation.class))).thenReturn(consultation);

        Consultation createdConsultation = consultationService.createConsultation(consultation);

        assertNotNull(createdConsultation);
        assertEquals("Follow-up visit", createdConsultation.getNotes());
        assertEquals(1L, createdConsultation.getPatient().getId());
        assertEquals(1L, createdConsultation.getDoctor().getId());

        verify(consultationRepository, times(1)).save(consultation);
        verify(patientRepository, times(1)).findById(anyLong());
        verify(doctorRepository, times(1)).findById(anyLong());
    }

    @Test
    void testCreateConsultation_PatientNotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            consultationService.createConsultation(consultation);
        });

        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    void testCreateConsultation_DoctorNotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(consultation.getPatient()));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            consultationService.createConsultation(consultation);
        });

        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    void testGetAllConsultations() {
        List<Consultation> consultations = new ArrayList<>();
        consultations.add(consultation);
        when(consultationRepository.findAll()).thenReturn(consultations);

        List<Consultation> result = consultationService.getAllConsultations();

        assertEquals(1, result.size());
        assertEquals("Follow-up visit", result.get(0).getNotes());
    }

    @Test
    void testFindConsultationById() {
        when(consultationRepository.findById(anyLong())).thenReturn(Optional.of(consultation));

        Consultation foundConsultation = consultationService.findConsultationById(1L);

        assertNotNull(foundConsultation);
        assertEquals("Follow-up visit", foundConsultation.getNotes());
    }

    @Test
    void testFindConsultationById_NotFound() {
        when(consultationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Consultation foundConsultation = consultationService.findConsultationById(1L);

        assertNull(foundConsultation);
    }

    @Test
    void testUpdateConsultation() {
        Consultation updatedConsultation = new Consultation();
        updatedConsultation.setPatient(consultation.getPatient());
        updatedConsultation.setDoctor(consultation.getDoctor());
        updatedConsultation.setNotes("Updated notes");

        when(consultationRepository.findById(anyLong())).thenReturn(Optional.of(consultation));
        when(consultationRepository.save(any(Consultation.class))).thenReturn(updatedConsultation);

        Consultation result = consultationService.updateConsultation(1L, updatedConsultation);

        assertNotNull(result);
        assertEquals("Updated notes", result.getNotes());
    }

    @Test
    void testUpdateConsultation_NotFound() {
        when(consultationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Consultation result = consultationService.updateConsultation(1L, consultation);

        assertNull(result);
    }

    @Test
    void testDeleteConsultation() {
        doNothing().when(consultationRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> consultationService.deleteConsultation(1L));

        verify(consultationRepository, times(1)).deleteById(1L);
    }
}

