package com.ansysan.cleverdev.service;

import com.ansysan.cleverdev.dto.PatientDto;
import com.ansysan.cleverdev.entity.Patient;
import com.ansysan.cleverdev.entity.User;
import com.ansysan.cleverdev.exception.NotFoundException;
import com.ansysan.cleverdev.mapper.PatientMapper;
import com.ansysan.cleverdev.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public Patient findById(long id) {
        log.debug("Find patient by id: {}", id);
        return patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient with id %s not found", id)));
    }

    public PatientDto save(PatientDto patientDto) {
        log.debug("Saving patient {}", patientDto);
        Patient patient = patientMapper.toEntity(patientDto);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    public PatientDto delete(Long id) {
        log.debug("Deleting patient {}", id);
        Patient patient = findById(id);
        patientRepository.deleteById(id);
        return patientMapper.toDto(patient);
    }

    public PatientDto getPatient(Long id) {
        log.debug("Getting patient {}", id);
        Patient patient = findById(id);
        return patientMapper.toDto(patient);
    }

    public Patient pathPatient(PatientDto patientDto, String guid) {
        log.debug("Pathing patient {}", patientDto);
        Patient patient = patientRepository.findByOldClientGuid(guid);
        patient.setFirstname(patientDto.getFirstName());
        patient.setLastname(patientDto.getLastName());
        patient.setOldClientGuid(patientDto.getOldClientGuid());
        patient.setStatusId(patientDto.getStatus());
        return patientRepository.save(patient);
    }
}