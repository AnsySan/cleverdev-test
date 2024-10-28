package com.ansysan.cleverdev.repository;

import com.ansysan.cleverdev.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByCreatedDateTime(LocalDateTime data);
}