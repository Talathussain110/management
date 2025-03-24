package com.patient.management.integration;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.patient.management.ManagementApplication;
import com.patient.management.entity.Doctor;
import com.patient.management.service.DoctorService;

@Testcontainers
@SpringBootTest(classes = ManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("ittest")
class DoctorWebControllerIntegrationTest {

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
	private DoctorService doctorService;

	@Test
	@Transactional
	void testListDoctors() throws Exception {
		Doctor doctor1 = new Doctor();
		doctor1.setName("Dr. Marco");
		doctor1.setSpecialization("Cardiology");

		doctorService.saveDoctor(doctor1);

		mockMvc.perform(get("/doctors")).andExpect(status().isOk()).andExpect(view().name("doctor/list-doctor"))
				.andExpect(model().attributeExists("doctors")).andExpect(model().attribute("doctors", hasSize(1)))
				.andExpect(model().attribute("doctors", hasItem(
						allOf(hasProperty("name", is("Dr. Marco")), hasProperty("specialization", is("Cardiology"))))));
	}

	@Test
	@Transactional
	void testAddDoctor() throws Exception {
		mockMvc.perform(post("/doctors/save").param("name", "Dr. Talat").param("specialization", "Neurology"))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/doctors"));
	}

	@Test
	@Transactional
	void testDeleteDoctor() throws Exception {
		Doctor doctor1 = new Doctor();
		doctor1.setName("Dr. Talat");
		doctor1.setSpecialization("Cardiology");
		doctor1 = doctorService.saveDoctor(doctor1);

		mockMvc.perform(get("/doctors/{id}/delete", doctor1.getId())).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/doctors"));
	}
}
