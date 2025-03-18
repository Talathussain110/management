package com.patient.management;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableAutoConfiguration
class ManagementApplicationTest {
	@Test
	void contextLoads() {
		assertTrue(true);
	}
}
