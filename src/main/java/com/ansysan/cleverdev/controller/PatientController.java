package com.ansysan.cleverdev.controller;

import com.ansysan.cleverdev.dto.PatientDto;
import com.ansysan.cleverdev.entity.Patient;
import com.ansysan.cleverdev.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    public PatientDto createNote(@RequestBody @Valid PatientDto patientDto) {
        return patientService.save(patientDto);
    }

    @DeleteMapping("/{patientId}")
    public PatientDto deleteNote(@PathVariable long patientId) {
        return patientService.delete(patientId);
    }

    @PutMapping("/update")
    public Patient updateNote(@RequestBody @Valid PatientDto patientDto, @PathVariable String guid) {
       return patientService.pathPatient(patientDto,guid);
    }

    @GetMapping("/get-patient")
    public PatientDto getPatient(@RequestParam Long id) {
        return patientService.getPatient(id);
    }

}
