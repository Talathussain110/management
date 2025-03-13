package com.patient.management.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.patient.management.entity.Patient;
import com.patient.management.repository.PatientRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient();
        patient.setId(1L);
        patient.setName("Talat Sethar");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setEmail("talatsethar@gmail.com");
    }

    @Test
    void testSavePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        Patient savedPatient = patientService.savePatient(patient);
        assertNotNull(savedPatient);
        assertEquals("Talat Sethar", savedPatient.getName());
    }

    @Test
    void testGetAllPatients() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient));
        List<Patient> patients = patientService.getAllPatients();
        assertFalse(patients.isEmpty());
        assertEquals(1, patients.size());
    }

    @Test
    void testGetPatientById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        Optional<Patient> retrievedPatient = patientService.getPatientById(1L);
        assertTrue(retrievedPatient.isPresent());
        assertEquals("Talat Sethar", retrievedPatient.get().getName());
    }

    @Test
    void testDeletePatientById() {
        doNothing().when(patientRepository).deleteById(1L);
        patientService.deletePatientById(1L);
        verify(patientRepository, times(1)).deleteById(1L);
    }
}
