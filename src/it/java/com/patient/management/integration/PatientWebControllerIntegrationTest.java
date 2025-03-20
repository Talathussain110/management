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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.patient.management.entity.Doctor;
import com.patient.management.entity.Patient;
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("ittest")
class PatientWebControllerIntegrationTest {

	@Container
	public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.1.0")
			.withUsername("patientmanagement").withPassword("patientmanagement").withDatabaseName("patientmanagement");

	@LocalServerPort
	private int port;

	@DynamicPropertySource
	static void databaseProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
		registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQL8Dialect");
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);
	}

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
