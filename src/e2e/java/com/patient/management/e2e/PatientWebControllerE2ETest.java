package com.patient.management.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.patient.management.entity.Doctor;
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class PatientWebControllerE2ETest {

	@Container
	public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.1.0")
			.withUsername("patientmanagement").withPassword("patientmanagement").withDatabaseName("patientmanagement");

	@LocalServerPort
	private int port;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	private WebDriver driver;
	private Doctor testDoctor;

	@DynamicPropertySource
	static void databaseProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
		registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQL8Dialect");
	}

	@BeforeEach
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		driver = new ChromeDriver(options);

		// Ensure at least one doctor exists for patient selection
		testDoctor = new Doctor();
		testDoctor.setName("Dr. Alice Johnson");
		testDoctor.setSpecialization("Pediatrics");
		testDoctor = doctorService.saveDoctor(testDoctor);
	}

	@AfterEach
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		patientService.getAllPatients().forEach(patient -> patientService.deletePatientById(patient.getId()));
		doctorService.getAllDoctors().forEach(doctor -> doctorService.deleteDoctorById(doctor.getId()));
	}

	@Test
	void testAddPatient() {
		// Given: Navigate to the add patient form
		driver.get("http://localhost:" + port + "/patients/new");

		// When: Fill out the form and submit
		WebElement nameField = driver.findElement(By.id("name"));
		nameField.sendKeys("John Doe");

		WebElement dateOfBirthField = driver.findElement(By.id("dateOfBirth"));
		dateOfBirthField.sendKeys(LocalDate.of(1990, 5, 15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("johndoe@example.com");

		WebElement doctorDropdown = driver.findElement(By.id("doctor"));
		doctorDropdown.sendKeys(testDoctor.getName()); // Select the test doctor

		WebElement submitButton = driver.findElement(By.tagName("button"));
		submitButton.click();

		// Then: Verify the patient is added
		driver.get("http://localhost:" + port + "/patients");
		WebElement patientTable = driver.findElement(By.tagName("table"));
		assertThat(patientTable.getText()).contains("John Doe").contains("johndoe@example.com")
				.contains(testDoctor.getName());
	}

	@Test
	void testDeletePatient() {
		// Given: Navigate to the add patient form
		driver.get("http://localhost:" + port + "/patients/new");

		// When: Fill out the form and submit
		WebElement nameField = driver.findElement(By.id("name"));
		nameField.sendKeys("Talat Sethar");

		WebElement dateOfBirthField = driver.findElement(By.id("dateOfBirth"));
		dateOfBirthField.sendKeys(LocalDate.of(1990, 5, 15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("johndoe@example.com");

		WebElement doctorDropdown = driver.findElement(By.id("doctor"));
		doctorDropdown.sendKeys(testDoctor.getName()); // Select the test doctor

		WebElement submitButton = driver.findElement(By.tagName("button"));
		submitButton.click();

		// When: Delete the patient
		driver.get("http://localhost:" + port + "/patients");
		WebElement deleteButton = driver
				.findElement(By.xpath("//td[contains(text(),'Talat Sethar')]/following-sibling::td/form/button"));
		deleteButton.click();

		// Handle the confirmation alert
		Alert alert = driver.switchTo().alert();
		alert.accept(); // Click "OK" to confirm deletion

		// Then: Verify the patient is deleted
		driver.get("http://localhost:" + port + "/patients");
		WebElement patientTable = driver.findElement(By.tagName("table"));
		assertThat(patientTable.getText()).doesNotContain("Talat Sethar");
	}
}
