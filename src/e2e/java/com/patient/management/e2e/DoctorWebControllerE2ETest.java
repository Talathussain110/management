package com.patient.management.e2e;

import static org.assertj.core.api.Assertions.assertThat;

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

import com.patient.management.service.DoctorService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DoctorWebControllerE2ETest {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.1.0")
            .withUsername("patientmanagement")
            .withPassword("patientmanagement")
            .withDatabaseName("patientmanagement");

    @LocalServerPort
    private int port;

    @Autowired
    private DoctorService doctorService;

    private WebDriver driver;

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
        options.addArguments("--headless"); // Run tests without opening a browser
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        doctorService.getAllDoctors().forEach(doctor -> doctorService.deleteDoctorById(doctor.getId()));
    }

    @Test
    void testAddDoctor() {
        // Given
        driver.get("http://localhost:" + port + "/doctors/new");

        // When
        WebElement nameField = driver.findElement(By.id("name"));
        nameField.sendKeys("Dr. John Doe");

        WebElement specializationField = driver.findElement(By.id("specialization"));
        specializationField.sendKeys("Cardiology");

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        // Then
        driver.get("http://localhost:" + port + "/doctors");
        WebElement doctorTable = driver.findElement(By.tagName("table"));
        assertThat(doctorTable.getText()).contains("Dr. John Doe");
    }

    @Test
    void testDeleteDoctor() {
        // Given: First, add a doctor
        driver.get("http://localhost:" + port + "/doctors/new");
        WebElement nameField = driver.findElement(By.id("name"));
        nameField.sendKeys("Dr. Talat Sethar");

        WebElement specializationField = driver.findElement(By.id("specialization"));
        specializationField.sendKeys("Neurology");

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        // When: Delete the doctor
        driver.get("http://localhost:" + port + "/doctors");
		WebElement deleteButton = driver
				.findElement(By.xpath("//td[contains(text(),'Dr. Talat Sethar')]/following-sibling::td/form/button"));
		deleteButton.click();

        // Handle the alert confirmation
        Alert alert = driver.switchTo().alert();
        alert.accept(); // Click "OK" on the confirmation alert

        // Then: Verify doctor is deleted
        driver.get("http://localhost:" + port + "/doctors");
        WebElement doctorTable = driver.findElement(By.tagName("table"));
        assertThat(doctorTable.getText()).doesNotContain("Dr. Talat Sethar");
    }
}
