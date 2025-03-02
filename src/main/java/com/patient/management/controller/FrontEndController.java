package com.patient.management.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.patient.management.entity.Doctor;
import com.patient.management.entity.Patient;
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;

@Controller
@RequestMapping("/view")
public class FrontEndController {

	private final PatientService patientService;
	private final DoctorService doctorService;

	public FrontEndController(PatientService patientService, DoctorService doctorService) {
		this.patientService = patientService;
		this.doctorService = doctorService;
	}

	// Display all Patients
	@GetMapping("/patients")
	public String viewPatients(Model model) {
		List<Patient> patients = patientService.getAllPatients();
		model.addAttribute("patients", patients);
		return "patients";
	}

	// Display all Doctors
	@GetMapping("/doctors")
	public String viewDoctors(Model model) {
		List<Doctor> doctors = doctorService.getAllDoctors();
		model.addAttribute("doctors", doctors);
		return "doctors";
	}

	// Form to add a new Patient
	@GetMapping("/patients/add")
	public String addPatientForm(Model model) {
		model.addAttribute("patient", new Patient());
		model.addAttribute("doctors", doctorService.getAllDoctors());
		return "add-patient";
	}

	// Save a new Patient
	@PostMapping("/patients")
	public String savePatient(@ModelAttribute Patient patient) {
		patientService.savePatient(patient);
		return "redirect:/view/patients";
	}
}
