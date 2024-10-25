package com.example.telemedicine_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Data
@Entity
@Table(name = "patient_details")
public class PatientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    private String address;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
}
