package com.patient.management.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.patient.management.ManagementApplication;
import com.patient.management.entity.Doctor;
import com.patient.management.repository.DoctorRepository;

@Testcontainers
@SpringBootTest(classes = ManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("ittest")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorControllerIntegrationTest {

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
	private DoctorRepository doctorRepository;

	@Autowired
	private ObjectMapper objectMapper; // Converts objects to JSON

	@AfterEach
	void tearDown() {
		doctorRepository.deleteAll(); // Clean up after each test
	}

	@Test
	void testCreateDoctor() throws Exception {
		Doctor doctor = new Doctor();
		doctor.setName("Dr. Marco");
		doctor.setSpecialization("Cardiology");

		mockMvc.perform(post("/api/doctors").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(doctor))).andExpect(status().isOk());

		List<Doctor> doctors = doctorRepository.findAll();
		assertThat(doctors).hasSize(1);
		assertThat(doctors.get(0).getName()).isEqualTo("Dr. Marco");
	}

	@Test
	void testGetAllDoctors() throws Exception {
		Doctor doctor1 = new Doctor();
		doctor1.setName("Dr. Alice");
		doctor1.setSpecialization("Neurology");

		Doctor doctor2 = new Doctor();
		doctor2.setName("Dr. Talat");
		doctor2.setSpecialization("Orthopedics");

		doctorRepository.saveAll(List.of(doctor1, doctor2));

		mockMvc.perform(get("/api/doctors")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void testGetDoctorById() throws Exception {
		Doctor doctor = new Doctor();
		doctor.setName("Dr. Talat");
		doctor.setSpecialization("Dermatology");
		doctor = doctorRepository.save(doctor);

		mockMvc.perform(get("/api/doctors/" + doctor.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Dr. Talat"));
	}

	@Test
	void testDeleteDoctor() throws Exception {
		Doctor doctor = new Doctor();
		doctor.setName("Dr. Talat");
		doctor.setSpecialization("Pediatrics");
		doctor = doctorRepository.save(doctor);

		mockMvc.perform(delete("/api/doctors/" + doctor.getId())).andExpect(status().isNoContent());

		assertThat(doctorRepository.findById(doctor.getId())).isEmpty();
	}
}
