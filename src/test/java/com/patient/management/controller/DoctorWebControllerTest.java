package com.patient.management.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.patient.management.entity.Doctor;
import com.patient.management.service.DoctorService;
import com.patient.management.service.PatientService;

@WebMvcTest(DoctorWebController.class)
public class DoctorWebControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DoctorService doctorService; // Mocking DoctorService

	@MockBean
	private PatientService patientService; // Mocking PatientService (if necessary)

	@InjectMocks
	private DoctorWebController doctorWebController; // Inject the mocks into the controller

	private Doctor doctor1;
	private Doctor doctor2;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		// Create some sample doctors
		doctor1 = new Doctor();
		doctor1.setId(1L);
		doctor1.setName("Dr. Talat Sethar");
		doctor1.setSpecialization("Cardiology");

		doctor2 = new Doctor();
		doctor2.setId(2L);
		doctor2.setName("Dr. Johnson");
		doctor2.setSpecialization("Neurology");
	}

	@Test
	public void testListDoctors() throws Exception {
		List<Doctor> doctors = Arrays.asList(doctor1, doctor2);
		when(doctorService.getAllDoctors()).thenReturn(doctors);

		mockMvc.perform(MockMvcRequestBuilders.get("/doctors")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("doctor/list-doctor"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("doctors"))
				.andExpect(MockMvcResultMatchers.model().attribute("doctors", doctors));
	}

	@Test
	public void testNewDoctorForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/doctors/new")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("doctor/add-doctor"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("doctor"));
	}

	@Test
	public void testSaveDoctor() throws Exception {
		when(doctorService.saveDoctor(any(Doctor.class))).thenReturn(doctor1);

		mockMvc.perform(MockMvcRequestBuilders.post("/doctors/save").param("name", "Dr. Talat Sethar")
				.param("specialization", "Cardiology")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/doctors"));

		verify(doctorService, times(1)).saveDoctor(any(Doctor.class));
	}

	@Test
	public void testDeleteDoctor() throws Exception {
		Long doctorId = 1L;
		doNothing().when(doctorService).deleteDoctorById(doctorId);

		mockMvc.perform(MockMvcRequestBuilders.get("/doctors/{id}/delete", doctorId))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/doctors"));

		verify(doctorService, times(1)).deleteDoctorById(doctorId);
	}
}
