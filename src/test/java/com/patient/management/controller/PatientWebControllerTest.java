package com.patient.management.controller;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.patient.management.entity.Doctor;
import com.patient.management.entity.Patient;
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;
@WebMvcTest(PatientWebController.class)
class PatientWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private DoctorService doctorService;

    @Test
    void listPatients_ShouldReturnPatientListPage() throws Exception {
        when(patientService.getAllPatients()).thenReturn(List.of(new Patient()));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/list"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    void newPatientForm_ShouldReturnPatientFormPageWithDoctors() throws Exception {
        when(doctorService.getAllDoctors()).thenReturn(List.of(new Doctor()));

        mockMvc.perform(get("/patients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/form"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("doctors"));
    }

    @Test
    void savePatient_ShouldRedirectAfterSaving() throws Exception {
        mockMvc.perform(post("/patients/save")
                        .param("name", "John Doe")
                        .param("dateOfBirth", "1990-05-15")
                        .param("email", "johndoe@example.com")
                        .param("doctor.id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }

    @Test
    void editPatientForm_ShouldReturnEditPageIfPatientExists() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));
        when(doctorService.getAllDoctors()).thenReturn(List.of(new Doctor()));

        mockMvc.perform(get("/patients/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/form"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("doctors"));
    }

    @Test
    void deletePatient_ShouldRedirectAfterDeletion() throws Exception {
        mockMvc.perform(get("/patients/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }
}
