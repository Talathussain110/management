package com.patient.management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.patient.management.repository.DoctorRepository;
import com.patient.management.repository.PatientRepository;

@SpringBootTest
@EnableAutoConfiguration
public class ManagementApplicationTest {
	@Test
	void contextLoads() {
	} // Your test cases
}
