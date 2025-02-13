package com.patient.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patient.management.entity.Patient;
import com.patient.management.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	// Create a new Patient
	@PostMapping
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
		return ResponseEntity.ok(patientService.savePatient(patient));
	}

	// Get all Patients
	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		return ResponseEntity.ok(patientService.getAllPatients());
	}

	// Get Patient by ID
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
		return patientService.getPatientById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// Update a Patient
	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
		if (!patientService.getPatientById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		patient.setId(id);
		return ResponseEntity.ok(patientService.savePatient(patient));
	}

	// Delete a Patient
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
		if (!patientService.getPatientById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		patientService.deletePatientById(id);
		return ResponseEntity.noContent().build();
	}
}
