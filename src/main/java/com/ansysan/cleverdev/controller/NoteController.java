package com.ansysan.cleverdev.controller;

import com.ansysan.cleverdev.dto.NoteDto;
import com.ansysan.cleverdev.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/create")
    public NoteDto createNote(@RequestBody @Valid NoteDto noteDto) {
        return noteService.createNote(noteDto);
    }

    @PutMapping("/publish/{noteId}")
    public NoteDto publishNote(@PathVariable Long noteId) {
        return noteService.publishNote(noteId);
    }

    @PutMapping("/{noteId}")
    public NoteDto updateNote(@PathVariable Long noteId, @RequestBody @Valid NoteDto noteDto) {
        return noteService.updateNote(noteId, noteDto);
    }

    @DeleteMapping("/{noteId}")
    public NoteDto deleteNote(@PathVariable Long noteId) {
        return noteService.deleteNote(noteId);
    }

    @GetMapping("/get-note")
    public Map<Long, String> showNoteForUser(@RequestParam String userLogin) {
        return noteService.showNotes(userLogin);
    }
}
