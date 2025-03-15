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
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;
@WebMvcTest(DoctorWebController.class)
class DoctorWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private PatientService patientService;

    @Test
    void listDoctors_ShouldReturnDoctorListPage() throws Exception {
        when(doctorService.getAllDoctors()).thenReturn(List.of(new Doctor()));

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor/list"))
                .andExpect(model().attributeExists("doctors"));
    }

    @Test
    void newDoctorForm_ShouldReturnDoctorFormPage() throws Exception {
        mockMvc.perform(get("/doctors/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor/form"))
                .andExpect(model().attributeExists("doctor"));
    }

    @Test
    void saveDoctor_ShouldRedirectAfterSaving() throws Exception {
        mockMvc.perform(post("/doctors/save")
                        .param("name", "Dr. Smith")
                        .param("specialization", "Cardiology"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctors"));
    }

    @Test
    void editDoctorForm_ShouldReturnEditPageIfDoctorExists() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));

        mockMvc.perform(get("/doctors/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor/form"))
                .andExpect(model().attributeExists("doctor"));
    }

    @Test
    void deleteDoctor_ShouldRedirectAfterDeletion() throws Exception {
        mockMvc.perform(get("/doctors/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctors"));
    }
}

