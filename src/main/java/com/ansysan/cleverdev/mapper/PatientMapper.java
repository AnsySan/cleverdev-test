package com.ansysan.cleverdev.mapper;

import com.ansysan.cleverdev.dto.PatientDto;
import com.ansysan.cleverdev.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

    Patient toEntity(PatientDto patientDto);

    PatientDto toDto(Patient patient);
}