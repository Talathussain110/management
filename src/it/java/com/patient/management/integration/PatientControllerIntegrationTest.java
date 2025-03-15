package com.patient.management.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.management.entity.Patient;
import com.patient.management.repository.PatientRepository;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("ittest")
class PatientControllerIntegrationTest {

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
	private PatientRepository patientRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@AfterEach
	void tearDown() {
		patientRepository.deleteAll(); // Clean up after each test
	}

	@Test
	void testGetAllPatients() throws Exception {
		Patient patient1 = new Patient();
		patient1.setName("Alice Smith");
		patient1.setDateOfBirth(LocalDate.of(1990, 3, 10));
		patient1.setEmail("alice@example.com");

		Patient patient2 = new Patient();
		patient2.setName("Bob Johnson");
		patient2.setDateOfBirth(LocalDate.of(1980, 8, 22));
		patient2.setEmail("bob@example.com");

		patientRepository.saveAll(List.of(patient1, patient2));

		mockMvc.perform(get("/api/patients")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void testGetPatientById() throws Exception {
		Patient patient = new Patient();
		patient.setName("Emily Davis");
		patient.setDateOfBirth(LocalDate.of(2000, 7, 1));
		patient.setEmail("emily@example.com");
		patient = patientRepository.save(patient);

		mockMvc.perform(get("/api/patients/" + patient.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Emily Davis"));
	}

	@Test
	void testUpdatePatient() throws Exception {
		Patient patient = new Patient();
		patient.setName("John Doe");
		patient.setDateOfBirth(LocalDate.of(1985, 5, 15));
		patient.setEmail("johndoe@example.com");
		patient = patientRepository.save(patient);

		patient.setName("John Doe Updated");
		patient.setEmail("updatedjohndoe@example.com");

		mockMvc.perform(put("/api/patients/" + patient.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(patient))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("John Doe Updated"))
				.andExpect(jsonPath("$.email").value("updatedjohndoe@example.com"));
	}

	@Test
	void testDeletePatient() throws Exception {
		Patient patient = new Patient();
		patient.setName("David Lee");
		patient.setDateOfBirth(LocalDate.of(1995, 2, 25));
		patient.setEmail("davidlee@example.com");
		patient = patientRepository.save(patient);

		mockMvc.perform(delete("/api/patients/" + patient.getId())).andExpect(status().isNoContent());

		assertThat(patientRepository.findById(patient.getId())).isEmpty();
	}
}
