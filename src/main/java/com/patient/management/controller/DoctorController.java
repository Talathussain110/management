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

import com.patient.management.entity.Doctor;
import com.patient.management.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

	private final DoctorService doctorService;

	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	// Create a new Doctor
	@PostMapping
	public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
		return ResponseEntity.ok(doctorService.saveDoctor(doctor));
	}

	// Get all Doctors
	@GetMapping
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		return ResponseEntity.ok(doctorService.getAllDoctors());
	}

	// Get Doctor by ID
	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
		return doctorService.getDoctorById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// Update a Doctor
	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
		if (!doctorService.getDoctorById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		doctor.setId(id);
		return ResponseEntity.ok(doctorService.saveDoctor(doctor));
	}

	// Delete a Doctor
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
		if (!doctorService.getDoctorById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		doctorService.deleteDoctorById(id);
		return ResponseEntity.noContent().build();
	}
}
