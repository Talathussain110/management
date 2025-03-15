package com.patient.management.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.patient.management.entity.Doctor;
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;

@Controller
@RequestMapping("/doctors")
public class DoctorWebController {

	private final DoctorService doctorService;
	private final PatientService patientService;

	public DoctorWebController(DoctorService doctorService, PatientService patientService) {
		this.doctorService = doctorService;
		this.patientService = patientService;
	}

	// 2. Get all doctors with their patients in a table
	@GetMapping
	public String listDoctors(Model model) {
		List<Doctor> doctors = doctorService.getAllDoctors();
		model.addAttribute("doctors", doctors);
		return "doctor/list"; // doctor/list.html
	}

	// 3. Add doctor form
	@GetMapping("/new")
	public String newDoctorForm(Model model) {
		model.addAttribute("doctor", new Doctor());
		return "doctor/form"; // doctor/form.html
	}

	// Save doctor (Create & Update)
	@PostMapping("/save")
	public String saveDoctor(@ModelAttribute Doctor doctor) {
		doctorService.saveDoctor(doctor);
		return "redirect:/doctors";
	}

	// Edit doctor form
	@GetMapping("/{id}/edit")
	public String editDoctorForm(@PathVariable Long id, Model model) {
		doctorService.getDoctorById(id).ifPresent(doctor -> model.addAttribute("doctor", doctor));
		return "doctor/form"; // doctor/form.html for editing
	}

	// Delete doctor
	@GetMapping("/{id}/delete")
	public String deleteDoctor(@PathVariable Long id) {
		doctorService.deleteDoctorById(id);
		return "redirect:/doctors";
	}
}
