package com.ansysan.cleverdev.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ansysan.cleverdev.dto.PatientDto;
import com.ansysan.cleverdev.entity.Patient;
import com.ansysan.cleverdev.entity.PatientStatus;
import com.ansysan.cleverdev.mapper.PatientMapper;
import com.ansysan.cleverdev.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;
    private PatientDto patientDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Инициализация тестовых данных
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstname("John");
        patient.setLastname("Doe");

        patientDto = new PatientDto();
        patientDto.setFirstName("John");
        patientDto.setLastName("Doe");
        patientDto.setOldClientGuid("12345");
        patientDto.setStatus(PatientStatus.ACTIVE);
    }

    @Test
    void save_ShouldReturnPatientDto_WhenPatientIsSaved() {
        when(patientMapper.toEntity(patientDto)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toDto(patient)).thenReturn(patientDto);

        PatientDto result = patientService.save(patientDto);

        assertNotNull(result);
        assertEquals(patientDto.getFirstName(), result.getFirstName());
        verify(patientMapper).toEntity(patientDto);
        verify(patientRepository).save(patient);
        verify(patientMapper).toDto(patient);
    }

    @Test
    void delete_ShouldReturnDeletedPatientDto_WhenPatientExists() {
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patient));
        when(patientMapper.toDto(patient)).thenReturn(patientDto);

        PatientDto result = patientService.delete(1L);

        assertNotNull(result);
        assertEquals(patientDto.getFirstName(), result.getFirstName());
        verify(patientRepository).findById(1L);
        verify(patientRepository).deleteById(1L);
    }

    @Test
    void delete_ShouldReturnNull_WhenPatientDoesNotExist() {
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patient));

        PatientDto result = patientService.delete(1L);

        assertNull(result); // Если пациента нет, возвращаем null
        verify(patientRepository).findById(1L);
        verify(patientRepository).deleteById(1L);
    }

    @Test
    void getPatient_ShouldReturnPatientDto_WhenPatientExists() {
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patient));
        when(patientMapper.toDto(patient)).thenReturn(patientDto);

        PatientDto result = patientService.getPatient(1L);

        assertNotNull(result);
        assertEquals(patientDto.getFirstName(), result.getFirstName());
        verify(patientRepository).findById(1L);
    }

    @Test
    void getPatient_ShouldReturnNull_WhenPatientDoesNotExist() {
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patient));

        PatientDto result = patientService.getPatient(1L);

        assertNull(result); // Если пациента нет, возвращаем null
        verify(patientRepository).findById(1L);
    }
}