package com.ansysan.cleverdev.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "company_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The field cannot be empty")
    @Column(unique = true, nullable = false, name = "login")
    private String login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdByUserId")
    private List<Note> listNoteForUserCreated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedByUserId")
    private List<Note> listNoteForUserUpdated;
}
