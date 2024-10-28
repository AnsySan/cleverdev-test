package com.ansysan.cleverdev.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_profile")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", length = 255)
    @Null
    private String firstname;

    @Column(name = "last_name", length = 255)
    @Null
    private String lastname;

    @Column(name = "old_client_guid", length = 255)
    @Null
    private String oldClientGuid;

    @Column(name = "status_id" , nullable = false)
    @Enumerated(EnumType.STRING)
    private PatientStatus statusId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "patient")
    private List<Note> listNoteForOnePatient;
}
