package com.example.telemedicine_backend.serviceTest;

import com.example.telemedicine_backend.model.Doctor;
import com.example.telemedicine_backend.repository.DoctorRepository;
import com.example.telemedicine_backend.service.DoctorService;
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

class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDoctors() {
        Doctor doctor1 = new Doctor();
        doctor1.setId(1L);
        doctor1.setName("Dr. Smith");

        Doctor doctor2 = new Doctor();
        doctor2.setId(2L);
        doctor2.setName("Dr. Jones");

        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.getAllDoctors();
        assertEquals(2, result.size());
        assertEquals("Dr. Smith", result.get(0).getName());
        assertEquals("Dr. Jones", result.get(1).getName());

        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testCreateDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Smith");
        doctor.setSpecialty("Cardiology");

        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        Doctor createdDoctor = doctorService.createDoctor(doctor);
        assertNotNull(createdDoctor);
        assertEquals("Dr. Smith", createdDoctor.getName());

        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void testFindDoctorById() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Smith");

        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));

        Doctor foundDoctor = doctorService.findDoctorById(1L);
        assertNotNull(foundDoctor);
        assertEquals("Dr. Smith", foundDoctor.getName());

        verify(doctorRepository, times(1)).findById(1L);
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Doctor existingDoctor = doctorRepository.findById(id).orElse(null);
        if (existingDoctor != null) {
            existingDoctor.setName(updatedDoctor.getName());
            existingDoctor.setSpecialty(updatedDoctor.getSpecialty());
            return doctorRepository.save(existingDoctor);
        }
        return null; // or throw an exception for not found
    }


    @Test
    void testDeleteDoctor() {
        doNothing().when(doctorRepository).deleteById(anyLong());

        doctorService.deleteDoctor(1L);

        verify(doctorRepository, times(1)).deleteById(1L);
    }
}