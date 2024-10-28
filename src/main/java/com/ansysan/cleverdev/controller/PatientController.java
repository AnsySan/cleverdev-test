package com.ansysan.cleverdev.controller;

import com.ansysan.cleverdev.dto.PatientDto;
import com.ansysan.cleverdev.entity.Patient;
import com.ansysan.cleverdev.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Creating patient")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public PatientDto createPatient(@RequestBody @Valid PatientDto patientDto) {
        log.info("Creating patient");
        return patientService.save(patientDto);
    }

    @Operation(summary = "Delete patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient deleted"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Error when deleting a patient")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{patientId}")
    public PatientDto deletePatient(@PathVariable long patientId) {
        log.info("Deleting patient");
        return patientService.delete(patientId);
    }

    @Operation(summary = "Update patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Error updating the patient")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public Patient updatePatient(@RequestBody @Valid PatientDto patientDto, @PathVariable String guid) {
        log.info("Updating patient");
       return patientService.pathPatient(patientDto,guid);
    }

    @Operation(summary = "Get patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient get"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Error get the patient")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-patient")
    public PatientDto getPatient(@RequestParam Long id) {
        log.info("Getting patient");
        return patientService.getPatient(id);
    }

}
