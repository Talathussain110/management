package com.patient.management.controller;


import com.patient.management.entity.Doctor;
import com.patient.management.entity.Patient;
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientWebController.class)
public class PatientWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private DoctorService doctorService;

    @InjectMocks
    private PatientWebController patientWebController;

    private Patient patient;
    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        // Create a mock Doctor object
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Talat Sethar");
        doctor.setSpecialization("Cardio");
        // Create a mock Patient object
        patient = new Patient();
        patient.setId(1L);
        patient.setName("Talat Sethar");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setEmail("talat@example.com");
        patient.setDoctor(doctor); // Setting the Doctor object for the patient
    }

    @Test
    public void testListPatients() throws Exception {
        // Mocking the PatientService to return a list of patients
        when(patientService.getAllPatients()).thenReturn(Collections.singletonList(patient));

        // Perform GET request to '/patients'
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/list-patient"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attribute("patients", Collections.singletonList(patient)));

        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    public void testNewPatientForm() throws Exception {
        // Mocking the DoctorService to return a list of doctors
        when(doctorService.getAllDoctors()).thenReturn(Collections.singletonList(doctor));

        // Perform GET request to '/patients/new'
        mockMvc.perform(get("/patients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/add-patient"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("doctors"));

        verify(doctorService, times(1)).getAllDoctors();
    }

//    @Test
//    public void testSavePatient() throws Exception {
//        // Perform POST request to '/patients/save'
//        mockMvc.perform(post("/patients/save")
//                        .param("name", "Talat Sethar")
//                        .param("dateOfBirth", "1990-01-01")
//                        .param("email", "talat@example.com")
//                        .param("doctor.id", "1"))  // Setting the doctor ID as part of the form
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/patients"));
//
//        verify(patientService, times(1)).savePatient(any(Patient.class));
//    }

    @Test
    public void testDeletePatient() throws Exception {
        // Perform GET request to '/patients/{id}/delete'
        mockMvc.perform(get("/patients/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));

        verify(patientService, times(1)).deletePatientById(1L);
    }
}
