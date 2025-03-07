//package com.patient.management.controller;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.patient.management.entity.Patient;
//import com.patient.management.service.PatientService;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(PatientController.class)
//class PatientControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PatientService patientService;
//
//    private Patient patient;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        patient = new Patient();
//        patient.setId(1L);
//        patient.setName("John Doe");
//        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
//        patient.setEmail("john.doe@example.com");
//    }
//
//    @Test
//    void testGetAllPatients() throws Exception {
//        when(patientService.getAllPatients()).thenReturn(Arrays.asList(patient));
//
//        mockMvc.perform(get("/api/patients"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("John Doe"));
//    }
//
//    @Test
//    void testGetPatientById() throws Exception {
//        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));
//
//        mockMvc.perform(get("/api/patients/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("John Doe"));
//    }
//
//    @Test
//    void testCreatePatient() throws Exception {
//        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);
//
//        mockMvc.perform(post("/api/patients")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"John Doe\",\"dateOfBirth\":\"1990-01-01\",\"email\":\"john.doe@example.com\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("John Doe"));
//    }
//
//    @Test
//    void testDeletePatient() throws Exception {
//        doNothing().when(patientService).deletePatientById(1L);
//
//        mockMvc.perform(delete("/api/patients/1"))
//                .andExpect(status().isNoContent());
//
//        verify(patientService, times(1)).deletePatientById(1L);
//    }
//}
