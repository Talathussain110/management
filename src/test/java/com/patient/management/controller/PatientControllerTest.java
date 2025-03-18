package com.patient.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.patient.management.entity.Patient;
import com.patient.management.service.PatientService;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

	@Mock
	private PatientService patientService;

	@InjectMocks
	private PatientController patientController;

	private Patient patient;

	@BeforeEach
	void setUp() {
		patient = new Patient();
		patient.setId(1L);
		patient.setName("Talat Sethar");
		patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
		patient.setEmail("talatsether@gmail.com");
	}

	@Test
	void testCreatePatient() {
		when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

		ResponseEntity<Patient> response = patientController.createPatient(patient);

		assertThat(response.getBody()).isEqualTo(patient);
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	void testGetAllPatients() {
		List<Patient> patients = Arrays.asList(patient);
		when(patientService.getAllPatients()).thenReturn(patients);

		ResponseEntity<List<Patient>> response = patientController.getAllPatients();

		assertThat(response.getBody()).hasSize(1);
		assertThat(response.getBody()).contains(patient);
	}

	@Test
	void testGetPatientById_Found() {
		when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));

		ResponseEntity<Patient> response = patientController.getPatientById(1L);

		assertThat(response.getBody()).isEqualTo(patient);
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	void testGetPatientById_NotFound() {
		when(patientService.getPatientById(1L)).thenReturn(Optional.empty());

		ResponseEntity<Patient> response = patientController.getPatientById(1L);

		assertThat(response.getStatusCode().value()).isEqualTo(404);
	}

	@Test
	void testUpdatePatient_Found() {
		when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));
		when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

		ResponseEntity<Patient> response = patientController.updatePatient(1L, patient);

		assertThat(response.getBody()).isEqualTo(patient);
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	void testUpdatePatient_NotFound() {
		when(patientService.getPatientById(1L)).thenReturn(Optional.empty());

		ResponseEntity<Patient> response = patientController.updatePatient(1L, patient);

		assertThat(response.getStatusCode().value()).isEqualTo(404);
	}

	@Test
	void testDeletePatient_Found() {
		when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));
		doNothing().when(patientService).deletePatientById(1L);

		ResponseEntity<Void> response = patientController.deletePatient(1L);

		assertThat(response.getStatusCode().value()).isEqualTo(204);
	}

	@Test
	void testDeletePatient_NotFound() {
		when(patientService.getPatientById(1L)).thenReturn(Optional.empty());

		ResponseEntity<Void> response = patientController.deletePatient(1L);

		assertThat(response.getStatusCode().value()).isEqualTo(404);
	}
}