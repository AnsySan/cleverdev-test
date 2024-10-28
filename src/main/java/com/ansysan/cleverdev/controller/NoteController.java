package com.ansysan.cleverdev.controller;

import com.ansysan.cleverdev.dto.NoteDto;
import com.ansysan.cleverdev.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "Create Note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note created"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public NoteDto createNote(@RequestBody @Valid NoteDto noteDto) {
        log.info("Create Note");
        return noteService.createNote(noteDto);
    }

    @Operation(summary = "Publishing a note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note published"),
            @ApiResponse(responseCode = "404", description = "Note not found"),
            @ApiResponse(responseCode = "500", description = "Error when publishing a note")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/publish/{noteId}")
    public NoteDto publishNote(@PathVariable Long noteId) {
        log.info("Publish Note");
        return noteService.publishNote(noteId);
    }

    @Operation(summary = "Update Note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated"),
            @ApiResponse(responseCode = "404", description = "Note not found"),
            @ApiResponse(responseCode = "500", description = "Error updating the note")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{noteId}")
    public NoteDto updateNote(@PathVariable Long noteId, @RequestBody @Valid NoteDto noteDto) {
        log.info("Update Note");
        return noteService.updateNote(noteId, noteDto);
    }

    @Operation(summary = "Deleting a note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note deleted"),
            @ApiResponse(responseCode = "404", description = "Note not found"),
            @ApiResponse(responseCode = "500", description = "Error when deleting a note")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{noteId}")
    public NoteDto deleteNote(@PathVariable Long noteId) {
        log.info("Delete Note");
        return noteService.deleteNote(noteId);
    }

    @Operation(summary = "Get note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note get"),
            @ApiResponse(responseCode = "404", description = "Note not found"),
            @ApiResponse(responseCode = "500", description = "Error get the note")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-note")
    public Map<Long, String> showNoteForUser(@RequestParam String userLogin) {
        log.info("Show Note");
        return noteService.showNotes(userLogin);
    }
}
