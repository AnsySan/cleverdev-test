package com.ansysan.cleverdev.service;


import com.ansysan.cleverdev.dto.NoteDto;
import com.ansysan.cleverdev.entity.Note;
import com.ansysan.cleverdev.entity.Patient;
import com.ansysan.cleverdev.entity.User;
import com.ansysan.cleverdev.exception.DataValidationException;
import com.ansysan.cleverdev.mapper.NoteMapper;
import com.ansysan.cleverdev.repository.NoteRepository;
import com.ansysan.cleverdev.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteMapper noteMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NoteService noteService;

    private NoteDto noteDto;
    private Note note;

    @BeforeEach
    void setUp() {
        noteDto = new NoteDto();
        note = new Note();
        note.setId(1L);
        note.setNote("Sample Note");
        note.setLastModifiedDateTime(LocalDateTime.now());
    }

    @Test
    void createNote_ShouldReturnNoteDto() {
        when(noteMapper.toEntity(noteDto)).thenReturn(note);
        when(noteRepository.save(note)).thenReturn(note);
        when(noteMapper.toDto(note)).thenReturn(noteDto);

        NoteDto result = noteService.createNote(noteDto);

        verify(noteMapper).toEntity(noteDto);
        verify(noteRepository).save(note);
        verify(noteMapper).toDto(note);
        assertEquals(result, noteDto);
    }


    @Test
    void updateNote_ShouldReturnUpdatedNoteDto() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(noteRepository.save(note)).thenReturn(note);
        when(noteMapper.toDto(note)).thenReturn(noteDto);

        NoteDto result = noteService.updateNote(1L, noteDto);

        verify(noteRepository).findById(1L);
        verify(noteRepository).save(note);
        verify(noteMapper).toDto(note);
        assertEquals(result, noteDto);
    }

    @Test
    void deleteNote_ShouldReturnDeletedNoteDto() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(noteMapper.toDto(note)).thenReturn(noteDto);

        NoteDto result = noteService.deleteNote(1L);

        verify(noteRepository).findById(1L);
        verify(noteRepository).deleteById(1L);
        verify(noteMapper).toDto(note);
        assertEquals(result, noteDto);
    }

    @Test
    void showNotes_ShouldReturnMapOfNotes() {
        User user = new User();
        user.setListNoteForUserCreated(List.of(note));

        when(userRepository.findByLogin("testLogin")).thenReturn(user);

        Map<Long, String> result = noteService.showNotes("testLogin");

        verify(userRepository).findByLogin("testLogin");
        assertEquals(result.size(), 1);
        assertEquals(result.get(1L), "Sample Note");
    }


    @Test
    void existsNote_ShouldThrowExceptionWhenNoteNotFound() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataValidationException.class, () -> {
            noteService.existsNote(1L);
        });

        assertEquals("Note with ID 1 not found", exception.getMessage());
    }
}
