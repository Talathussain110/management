//package com.patient.management.integration;
//
//import com.patient.management.entity.Doctor;
//import com.patient.management.repository.DoctorRepository;
//import com.patient.management.service.DoctorService;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.TestPropertySource;
//
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Testcontainers
//@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
//class DoctorServiceIntegrationTest {
////
////    @Container
////    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
////            .withDatabaseName("test_db")
////            .withUsername("test_user")
////            .withPassword("test_password");
//
//    @Autowired
//    private DoctorService doctorService;
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
////    @DynamicPropertySource
////    static void configureProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
////        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
////        registry.add("spring.datasource.username", mysqlContainer::getUsername);
////        registry.add("spring.datasource.password", mysqlContainer::getPassword);
////    }
//
//    @AfterEach
//    void tearDown() {
//        doctorRepository.deleteAll();
//    }
//
//    @Test
//    void testSaveAndRetrieveDoctor() {
//        Doctor doctor = new Doctor();
//        doctor.setName("Dr. Smith");
//        doctor.setSpecialization("Cardiology");
//
//        Doctor savedDoctor = doctorService.saveDoctor(doctor);
//        assertNotNull(savedDoctor.getId());
//
//        Doctor retrievedDoctor = doctorService.getDoctorById(savedDoctor.getId()).orElse(null);
//        assertNotNull(retrievedDoctor);
//        assertEquals("Dr. Smith", retrievedDoctor.getName());
//    }
//}
