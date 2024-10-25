package com.example.telemedicine_backend.serviceTest;

import com.example.telemedicine_backend.model.Patient;
import com.example.telemedicine_backend.model.PatientDetails;
import com.example.telemedicine_backend.repository.PatientDetailsRepository;
import com.example.telemedicine_backend.repository.PatientRepository;
import com.example.telemedicine_backend.service.PatientDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientDetailsServiceTest {

    private PatientDetailsService patientDetailsService;
    private PatientDetailsRepository patientDetailsRepository;
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientDetailsRepository = mock(PatientDetailsRepository.class);
        patientRepository = mock(PatientRepository.class);
        patientDetailsService = new PatientDetailsService(patientDetailsRepository, patientRepository);
    }

    @Test
    void createPatientDetails_ShouldSaveAndReturnDetails() {
        Patient patient = new Patient();
        patient.setId(1L);

        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setPatient(patient);
        patientDetails.setAddress("123 Main St");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientDetailsRepository.save(patientDetails)).thenReturn(patientDetails);

        PatientDetails createdDetails = patientDetailsService.createPatientDetails(patientDetails);

        assertNotNull(createdDetails);
        assertEquals("123 Main St", createdDetails.getAddress());
        verify(patientDetailsRepository, times(1)).save(patientDetails);
    }

    @Test
    void createPatientDetails_ShouldThrowException_WhenPatientNotFound() {
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setPatient(new Patient());
        patientDetails.getPatient().setId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientDetailsService.createPatientDetails(patientDetails);
        });

        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    void getAllPatientDetails_ShouldReturnListOfDetails() {
        PatientDetails patientDetails1 = new PatientDetails();
        PatientDetails patientDetails2 = new PatientDetails();
        when(patientDetailsRepository.findAll()).thenReturn(List.of(patientDetails1, patientDetails2));

        List<PatientDetails> detailsList = patientDetailsService.getAllPatientDetails();

        assertEquals(2, detailsList.size());
        verify(patientDetailsRepository, times(1)).findAll();
    }

    @Test
    void getPatientDetailsById_ShouldReturnDetails_WhenFound() {
        PatientDetails patientDetails = new PatientDetails();
        when(patientDetailsRepository.findById(1L)).thenReturn(Optional.of(patientDetails));

        PatientDetails foundDetails = patientDetailsService.getPatientDetailsById(1L);

        assertNotNull(foundDetails);
        verify(patientDetailsRepository, times(1)).findById(1L);
    }

    @Test
    void getPatientDetailsById_ShouldReturnNull_WhenNotFound() {
        when(patientDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        PatientDetails foundDetails = patientDetailsService.getPatientDetailsById(1L);

        assertNull(foundDetails);
        verify(patientDetailsRepository, times(1)).findById(1L);
    }

    @Test
    void updatePatientDetails_ShouldUpdateAndReturnDetails_WhenFound() {
        PatientDetails existingDetails = new PatientDetails();
        existingDetails.setAddress("Old Address");

        PatientDetails updatedDetails = new PatientDetails();
        updatedDetails.setAddress("New Address");

        when(patientDetailsRepository.findById(1L)).thenReturn(Optional.of(existingDetails));
        when(patientDetailsRepository.save(existingDetails)).thenReturn(existingDetails);

        PatientDetails result = patientDetailsService.updatePatientDetails(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("New Address", result.getAddress());
        verify(patientDetailsRepository, times(1)).save(existingDetails);
    }

    @Test
    void updatePatientDetails_ShouldReturnNull_WhenNotFound() {
        PatientDetails updatedDetails = new PatientDetails();
        when(patientDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        PatientDetails result = patientDetailsService.updatePatientDetails(1L, updatedDetails);

        assertNull(result);
        verify(patientDetailsRepository, times(0)).save(any());
    }

    @Test
    void deletePatientDetails_ShouldCallDeleteById() {
        patientDetailsService.deletePatientDetails(1L);

        verify(patientDetailsRepository, times(1)).deleteById(1L);
    }
}



