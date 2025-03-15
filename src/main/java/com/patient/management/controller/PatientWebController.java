package com.patient.management.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.patient.management.entity.Patient;
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;

@Controller
@RequestMapping("/patients")
public class PatientWebController {

	private final PatientService patientService;
	private final DoctorService doctorService;

	public PatientWebController(PatientService patientService, DoctorService doctorService) {
		this.patientService = patientService;
		this.doctorService = doctorService;
	}

	// 1. Get all patients with their doctor names in a table
	@GetMapping
	public String listPatients(Model model) {
		List<Patient> patients = patientService.getAllPatients();
		model.addAttribute("patients", patients);
		return "patient/list-patient"; // patient/list.html
	}

	// 4. Add patient form with doctor dropdown
	@GetMapping("/new")
	public String newPatientForm(Model model) {
		model.addAttribute("patient", new Patient());
		model.addAttribute("doctors", doctorService.getAllDoctors()); // Dropdown for doctors
		return "patient/add-patient"; // patient/form.html
	}

	// Save patient (Create & Update)
	@PostMapping("/save")
	public String savePatient(@ModelAttribute Patient patient) {
		patientService.savePatient(patient);
		return "redirect:/patients";
	}

	// Delete patient
	@GetMapping("/{id}/delete")
	public String deletePatient(@PathVariable Long id) {
		patientService.deletePatientById(id);
		return "redirect:/patients";
	}
}
