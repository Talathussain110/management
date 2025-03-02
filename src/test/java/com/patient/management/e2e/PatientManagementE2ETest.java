//package com.patient.management.e2e;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PatientManagementE2ETest {
//
//    private static WebDriver driver;
//
//    @BeforeAll
//    static void setup() {
//        // Set path for ChromeDriver (ensure you have the correct version of ChromeDriver installed)
//        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // Optional: Run in headless mode for CI/CD
//        driver = new ChromeDriver(options);
//    }
//
//    @Test
//    void testAddPatient() {
//        // Navigate to the page displaying all patients
//        driver.get("http://localhost:8080/view/patients");
//
//        // Click on the "Add New Patient" link
//        driver.findElement(By.linkText("Add New Patient")).click();
//
//        // Fill the form with patient data
//        driver.findElement(By.id("name")).sendKeys("Jane Doe");
//        driver.findElement(By.id("dateOfBirth")).sendKeys("1995-05-15");
//        driver.findElement(By.id("email")).sendKeys("jane.doe@example.com");
//
//        // Select the doctor
//        driver.findElement(By.id("doctor")).sendKeys("Dr. Smith");
//
//        // Submit the form
//        driver.findElement(By.cssSelector("button[type='submit']")).click();
//
//        // Wait for the page to reload
//        try {
//            Thread.sleep(2000); // Just for demonstration, use WebDriverWait for better practices
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Verify if the new patient is displayed in the list
//        String patientName = driver.findElement(By.xpath("//tr[td[text()='Jane Doe']]")).getText();
//        assertTrue(patientName.contains("Jane Doe"));
//    }
//
//    @AfterAll
//    static void tearDown() {
//        // Close the browser after the test
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}
//
