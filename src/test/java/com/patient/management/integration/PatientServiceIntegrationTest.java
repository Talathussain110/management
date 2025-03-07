//package com.patient.management.integration;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.time.LocalDate;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.TestPropertySource;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import com.patient.management.entity.Patient;
//import com.patient.management.repository.PatientRepository;
//import com.patient.management.service.PatientService;
//
//@SpringBootTest
//@Testcontainers
//@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=create-drop" })
//class PatientServiceIntegrationTest {
//
//	@Container
//	private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
//			.withDatabaseName("test_db").withUsername("test_user").withPassword("test_password");
//
//	@Autowired
//	private PatientService patientService;
//
//	@Autowired
//	private PatientRepository patientRepository;
//
//	@DynamicPropertySource
//	static void configureProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
//		registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
//		registry.add("spring.datasource.username", mysqlContainer::getUsername);
//		registry.add("spring.datasource.password", mysqlContainer::getPassword);
//	}
//
//	@AfterEach
//	void tearDown() {
//		patientRepository.deleteAll();
//	}
//
//	@Test
//	void testSaveAndRetrievePatient() {
//		Patient patient = new Patient();
//		patient.setName("Jane Doe");
//		patient.setDateOfBirth(LocalDate.of(1995, 5, 15));
//		patient.setEmail("jane.doe@example.com");
//
//		Patient savedPatient = patientService.savePatient(patient);
//		assertNotNull(savedPatient.getId());
//
//		Patient retrievedPatient = patientService.getPatientById(savedPatient.getId()).orElse(null);
//		assertNotNull(retrievedPatient);
//		assertEquals("Jane Doe", retrievedPatient.getName());
//	}
//}
