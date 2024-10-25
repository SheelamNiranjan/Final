package com.example.telemedicine_backend.controllerTest;


import com.example.telemedicine_backend.controller.ConsultationController;
import com.example.telemedicine_backend.model.Consultation;
import com.example.telemedicine_backend.service.ConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
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

class ConsultationControllerTest {

    @Mock
    private ConsultationService consultationService;

    @InjectMocks
    private ConsultationController consultationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(consultationController).build();
    }

    @Test
    void testCreateConsultation() throws Exception {
        Consultation consultation = new Consultation();
        consultation.setId(1L);
        consultation.setPatient(null); // Set actual patient if you have a Patient object
        consultation.setDoctor(null); // Set actual doctor if you have a Doctor object
        consultation.setScheduledDate(LocalDateTime.now());
        consultation.setNotes("Follow-up visit");

        when(consultationService.createConsultation(any(Consultation.class))).thenReturn(consultation);

        mockMvc.perform(post("/api/consultations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"patient\":null,\"doctor\":null,\"scheduledDate\":\"2024-10-22T10:15:30\",\"notes\":\"Follow-up visit\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").value("Follow-up visit"));

        verify(consultationService, times(1)).createConsultation(any(Consultation.class));
    }

    @Test
    void testGetAllConsultations() throws Exception {
        Consultation consultation1 = new Consultation();
        consultation1.setId(1L);
        consultation1.setNotes("Initial consultation");

        Consultation consultation2 = new Consultation();
        consultation2.setId(2L);
        consultation2.setNotes("Follow-up visit");

        List<Consultation> consultations = Arrays.asList(consultation1, consultation2);

        when(consultationService.getAllConsultations()).thenReturn(consultations);

        mockMvc.perform(get("/api/consultations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].notes").value("Initial consultation"))
                .andExpect(jsonPath("$[1].notes").value("Follow-up visit"));

        verify(consultationService, times(1)).getAllConsultations();
    }

    @Test
    void testGetConsultationById() throws Exception {
        Consultation consultation = new Consultation();
        consultation.setId(1L);
        consultation.setNotes("Initial consultation");

        when(consultationService.findConsultationById(anyLong())).thenReturn(consultation);

        mockMvc.perform(get("/api/consultations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").value("Initial consultation"));

        verify(consultationService, times(1)).findConsultationById(1L);
    }

    @Test
    void testUpdateConsultation() throws Exception {
        Consultation existingConsultation = new Consultation();
        existingConsultation.setId(1L);
        existingConsultation.setNotes("Initial consultation");

        Consultation updatedConsultation = new Consultation();
        updatedConsultation.setId(1L); // Ensure this ID is set for the response
        updatedConsultation.setNotes("Updated notes");

        // When updating, we use matchers for both parameters
        when(consultationService.updateConsultation(anyLong(), any(Consultation.class)))
                .thenReturn(updatedConsultation);

        mockMvc.perform(put("/api/consultations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"notes\":\"Updated notes\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").value("Updated notes"));

        verify(consultationService, times(1)).updateConsultation(eq(1L), any(Consultation.class));
    }
    @Test
    void testDeleteConsultation() throws Exception {
        doNothing().when(consultationService).deleteConsultation(anyLong());

        mockMvc.perform(delete("/api/consultations/1"))
                .andExpect(status().isOk());

        verify(consultationService, times(1)).deleteConsultation(1L);
    }
}