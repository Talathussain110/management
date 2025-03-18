package com.patient.management.integration;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.patient.management.entity.Doctor;
import com.patient.management.entity.Patient;
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;

@SpringBootTest
@AutoConfigureMockMvc
class PatientWebControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PatientService patientService;

	@Autowired
	private DoctorService doctorService;

	@Test
	@Transactional
	void testListPatients() throws Exception {
		// Create a doctor
		Doctor doctor1 = new Doctor();
		doctor1.setName("Dr. Talat Sethar");
		doctor1.setSpecialization("Cardiology");
		doctor1 = doctorService.saveDoctor(doctor1);

		// Create a patient with a dateOfBirth
		Patient patient1 = new Patient();
		patient1.setName("Talat Sethar");
		patient1.setEmail("talat@example.com");
		patient1.setDoctor(doctor1);
		patient1.setDateOfBirth(LocalDate.of(1990, 1, 1)); // Add dateOfBirth
		patientService.savePatient(patient1);

		mockMvc.perform(get("/patients")).andExpect(status().isOk()).andExpect(view().name("patient/list-patient"))
				.andExpect(model().attributeExists("patients")).andExpect(model().attribute("patients", hasSize(1)))
				.andExpect(model().attribute("patients",
						hasItem(allOf(hasProperty("name", is("Talat Sethar")),
								hasProperty("email", is("talat@example.com")),
								hasProperty("doctor", hasProperty("name", is("Dr. Talat Sethar"))),
								hasProperty("dateOfBirth", is(LocalDate.of(1990, 1, 1))) // Check dateOfBirth
						))));
	}

//	@Test
//	@Transactional
//	public void testAddPatient() throws Exception {
//		// Create a doctor
//		Doctor doctor1 = new Doctor();
//		doctor1.setName("Dr. Talat");
//		doctor1.setSpecialization("Neurology");
//		doctor1 = doctorService.saveDoctor(doctor1);
//
//		mockMvc.perform(post("/patients/save").param("name", "Talat Sethar").param("email", "talat@example.com")
//				.param("doctor.id", doctor1.getId().toString()).param("dateOfBirth", "1985-05-10")) // Provide
//																									// dateOfBirth in
//																									// the form of
//																									// YYYY-MM-DD
//				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/patients"));
//	}

	@Test
	@Transactional
	void testDeletePatient() throws Exception {
		// Create a doctor
		Doctor doctor1 = new Doctor();
		doctor1.setName("Dr. Talat Sethar");
		doctor1.setSpecialization("Cardiology");
		doctor1 = doctorService.saveDoctor(doctor1);

		// Create a patient with a dateOfBirth
		Patient patient1 = new Patient();
		patient1.setName("Talat Sethar");
		patient1.setEmail("talat@example.com");
		patient1.setDoctor(doctor1);
		patient1.setDateOfBirth(LocalDate.of(1990, 1, 1)); // Add dateOfBirth
		patient1 = patientService.savePatient(patient1);

		mockMvc.perform(get("/patients/{id}/delete", patient1.getId())).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/patients"));
	}
}
