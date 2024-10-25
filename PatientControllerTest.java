package com.example.telemedicine_backend.controllerTest;

import com.example.telemedicine_backend.controller.PatientController;
import com.example.telemedicine_backend.model.Patient;
import com.example.telemedicine_backend.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllPatients() throws Exception {
        List<Patient> patients = Arrays.asList(
                new Patient(1L, "John Doe", "john@example.com", "password123"),
                new Patient(2L, "Jane Doe", "jane@example.com", "password456")
        );

        when(patientService.findAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andDo(print());
    }

    @Test
    public void testRegisterPatient() throws Exception {
        Patient patient = new Patient(1L, "John Doe", "john@example.com", "password123");

        when(patientService.registerPatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andDo(print());
    }

    @Test
    public void testGetPatientByEmail() throws Exception {
        Patient patient = new Patient(1L, "John Doe", "john@example.com", "password123");

        when(patientService.findByEmail("john@example.com")).thenReturn(patient);

        mockMvc.perform(get("/api/patients/john@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andDo(print());
    }

    @Test
    public void testUpdatePatient() throws Exception {
        Patient updatedPatient = new Patient(1L, "John Smith", "john@example.com", "newpassword123");

        when(patientService.updatePatient(Mockito.eq(1L), any(Patient.class))).thenReturn(updatedPatient);

        mockMvc.perform(put("/api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.password").value("newpassword123"))
                .andDo(print());
    }

    @Test
    public void testDeletePatient() throws Exception {
        mockMvc.perform(delete("/api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(patientService).deletePatient(1L);
    }
}
