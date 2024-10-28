package com.ansysan.cleverdev.repository;

import com.ansysan.cleverdev.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findById(long id);
    Patient findByOldClientGuid(String guid);
}
