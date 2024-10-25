package com.example.telemedicine_backend.controllerTest;

import com.example.telemedicine_backend.controller.PatientDetailsController;
import com.example.telemedicine_backend.model.PatientDetails;
import com.example.telemedicine_backend.service.PatientDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PatientDetailsControllerTest {

    private MockMvc mockMvc;
    private PatientDetailsService patientDetailsService;
    private PatientDetailsController patientDetailsController;

    @BeforeEach
    void setUp() {
        patientDetailsService = mock(PatientDetailsService.class);
        patientDetailsController = new PatientDetailsController();
        patientDetailsController.setPatientDetailsService(patientDetailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(patientDetailsController).build();
    }

    @Test
    void createPatientDetails_ShouldReturnCreatedDetails() throws Exception {
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setAddress("123 Main St");

        when(patientDetailsService.createPatientDetails(any(PatientDetails.class))).thenReturn(patientDetails);

        mockMvc.perform(post("/api/patient-details")
                        .contentType("application/json")
                        .content("{\"address\":\"123 Main St\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    void getAllPatientDetails_ShouldReturnList() throws Exception {
        PatientDetails patientDetails1 = new PatientDetails();
        PatientDetails patientDetails2 = new PatientDetails();
        when(patientDetailsService.getAllPatientDetails()).thenReturn(List.of(patientDetails1, patientDetails2));

        mockMvc.perform(get("/api/patient-details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").hasJsonPath());
    }

    @Test
    void getPatientDetailsById_ShouldReturnDetails_WhenFound() throws Exception {
        PatientDetails patientDetails = new PatientDetails();
        when(patientDetailsService.getPatientDetailsById(1L)).thenReturn(patientDetails);

        mockMvc.perform(get("/api/patient-details/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void getPatientDetailsById_ShouldReturnNotFound_WhenNotFound() throws Exception {
        when(patientDetailsService.getPatientDetailsById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/patient-details/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePatientDetails_ShouldReturnUpdatedDetails_WhenFound() throws Exception {
        PatientDetails updatedDetails = new PatientDetails();
        updatedDetails.setAddress("New Address");

        when(patientDetailsService.updatePatientDetails(1L, updatedDetails)).thenReturn(updatedDetails);

        mockMvc.perform(put("/api/patient-details/1")
                        .contentType("application/json")
                        .content("{\"address\":\"New Address\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("New Address"));
    }

    @Test
    void updatePatientDetails_ShouldReturnNotFound_WhenNotFound() throws Exception {
        PatientDetails updatedDetails = new PatientDetails();
        when(patientDetailsService.updatePatientDetails(1L, updatedDetails)).thenReturn(null);

        mockMvc.perform(put("/api/patient-details/1")
                        .contentType("application/json")
                        .content("{\"address\":\"New Address\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePatientDetails_ShouldReturnNoContent() throws Exception {
        doNothing().when(patientDetailsService).deletePatientDetails(1L);

        mockMvc.perform(delete("/api/patient-details/1"))
                .andExpect(status().isNoContent());
    }
}
