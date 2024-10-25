package com.example.telemedicine_backend.serviceTest;

import com.example.telemedicine_backend.model.Patient;
import com.example.telemedicine_backend.repository.PatientRepository;
import com.example.telemedicine_backend.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");
        patient.setEmail("john.doe@example.com");

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient registeredPatient = patientService.registerPatient(patient);
        assertEquals("John Doe", registeredPatient.getName());
        assertEquals("john.doe@example.com", registeredPatient.getEmail());

        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void testFindByEmail() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("john.doe@example.com");

        when(patientRepository.findByEmail("john.doe@example.com")).thenReturn(patient);

        Patient foundPatient = patientService.findByEmail("john.doe@example.com");
        assertNotNull(foundPatient);
        assertEquals("john.doe@example.com", foundPatient.getEmail());

        verify(patientRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void testDeletePatient() {
        doNothing().when(patientRepository).deleteById(anyLong());

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdatePatient() {
        Patient existingPatient = new Patient();
        existingPatient.setId(1L);
        existingPatient.setName("John Doe");
        existingPatient.setEmail("john.doe@example.com");

        Patient updatedPatient = new Patient();
        updatedPatient.setName("Jane Doe");
        updatedPatient.setEmail("jane.doe@example.com");

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        Patient result = patientService.updatePatient(1L, updatedPatient);
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane.doe@example.com", result.getEmail());

        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).save(existingPatient);
    }

    @Test
    void testFindAllPatients() {
        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setName("John Doe");

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setName("Jane Doe");

        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.findAllPatients();
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());

        verify(patientRepository, times(1)).findAll();
    }
}