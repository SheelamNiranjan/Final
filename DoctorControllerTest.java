package com.example.telemedicine_backend.controllerTest;

import com.example.telemedicine_backend.controller.DoctorController;
import com.example.telemedicine_backend.model.Doctor;
import com.example.telemedicine_backend.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    void testGetAllDoctors() throws Exception {
        Doctor doctor1 = new Doctor();
        doctor1.setId(1L);
        doctor1.setName("Dr. Smith");

        Doctor doctor2 = new Doctor();
        doctor2.setId(2L);
        doctor2.setName("Dr. Jones");

        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        when(doctorService.getAllDoctors()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Dr. Smith"))
                .andExpect(jsonPath("$[1].name").value("Dr. Jones"));

        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    void testCreateDoctor() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Smith");
        doctor.setSpecialty("Cardiology");

        when(doctorService.createDoctor(any(Doctor.class))).thenReturn(doctor);

        mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Dr. Smith\",\"specialty\":\"Cardiology\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. Smith"));

        verify(doctorService, times(1)).createDoctor(any(Doctor.class));
    }

    @Test
    void testGetDoctorById() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Smith");

        when(doctorService.findDoctorById(anyLong())).thenReturn(doctor);

        mockMvc.perform(get("/api/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. Smith"));

        verify(doctorService, times(1)).findDoctorById(1L);
    }

    @Test
    void testUpdateDoctor() throws Exception {
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId(1L);
        existingDoctor.setName("Dr. Smith");

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setName("Dr. Johnson");
        updatedDoctor.setSpecialty("Neurology");

        when(doctorService.updateDoctor(anyLong(), any(Doctor.class))).thenReturn(updatedDoctor);

        mockMvc.perform(put("/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Dr. Johnson\",\"specialty\":\"Neurology\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. Johnson"));

        verify(doctorService, times(1)).updateDoctor(eq(1L), any(Doctor.class));
    }

    @Test
    void testDeleteDoctor() throws Exception {
        doNothing().when(doctorService).deleteDoctor(anyLong());

        mockMvc.perform(delete("/api/doctors/1"))
                .andExpect(status().isOk());

        verify(doctorService, times(1)).deleteDoctor(1L);
    }
}
