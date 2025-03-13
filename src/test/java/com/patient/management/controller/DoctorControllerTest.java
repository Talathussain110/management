package com.patient.management.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.patient.management.entity.Doctor;
import com.patient.management.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Talat Sethar");
        doctor.setSpecialization("Cardiology");
    }

    @Test
    void testCreateDoctor() {
        when(doctorService.saveDoctor(any(Doctor.class))).thenReturn(doctor);
        ResponseEntity<Doctor> response = doctorController.createDoctor(doctor);
        assertNotNull(response.getBody());
        assertEquals(doctor.getName(), response.getBody().getName());
    }

    @Test
    void testGetAllDoctors() {
        List<Doctor> doctors = Arrays.asList(doctor);
        when(doctorService.getAllDoctors()).thenReturn(doctors);
        ResponseEntity<List<Doctor>> response = doctorController.getAllDoctors();
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetDoctorById_Found() {
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));
        ResponseEntity<Doctor> response = doctorController.getDoctorById(1L);
        assertEquals(doctor.getId(), response.getBody().getId());
    }

    @Test
    void testGetDoctorById_NotFound() {
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Doctor> response = doctorController.getDoctorById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateDoctor_Found() {
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));
        when(doctorService.saveDoctor(any(Doctor.class))).thenReturn(doctor);
        ResponseEntity<Doctor> response = doctorController.updateDoctor(1L, doctor);
        assertEquals(doctor.getId(), response.getBody().getId());
    }

    @Test
    void testUpdateDoctor_NotFound() {
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Doctor> response = doctorController.updateDoctor(1L, doctor);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteDoctor_Found() {
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorService).deleteDoctorById(1L);
        ResponseEntity<Void> response = doctorController.deleteDoctor(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteDoctor_NotFound() {
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = doctorController.deleteDoctor(1L);
        assertEquals(404, response.getStatusCodeValue());
    }
}
