package com.patient.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.patient.management.entity.Doctor;
import com.patient.management.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Talat Sethar");
        doctor.setSpecialization("Cardiology");
    }

    @Test
    void testSaveDoctor() {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        assertNotNull(savedDoctor);
        assertEquals(doctor.getId(), savedDoctor.getId());
    }

    @Test
    void testGetAllDoctors() {
        List<Doctor> doctorList = Arrays.asList(doctor);
        when(doctorRepository.findAll()).thenReturn(doctorList);
        List<Doctor> result = doctorService.getAllDoctors();
        assertEquals(1, result.size());
    }

    @Test
    void testGetDoctorById() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        Optional<Doctor> foundDoctor = doctorService.getDoctorById(1L);
        assertTrue(foundDoctor.isPresent());
        assertEquals(doctor.getId(), foundDoctor.get().getId());
    }

    @Test
    void testDeleteDoctorById() {
        doNothing().when(doctorRepository).deleteById(1L);
        doctorService.deleteDoctorById(1L);
        verify(doctorRepository, times(1)).deleteById(1L);
    }
}
