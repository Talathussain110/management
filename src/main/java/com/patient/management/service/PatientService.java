package com.patient.management.service;

import org.springframework.stereotype.Service;

import com.patient.management.entity.Patient;
import com.patient.management.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Create or Update a Patient
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Get all Patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Get a Patient by ID
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    // Delete a Patient by ID
    public void deletePatientById(Long id) {
        patientRepository.deleteById(id);
    }

    // Get Patients by Doctor ID
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        return patientRepository.findByDoctorId(doctorId);
    }
}

