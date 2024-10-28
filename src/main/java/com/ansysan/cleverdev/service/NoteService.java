package com.ansysan.cleverdev.service;

import com.ansysan.cleverdev.dto.NoteDto;
import com.ansysan.cleverdev.entity.Note;
import com.ansysan.cleverdev.entity.Patient;
import com.ansysan.cleverdev.entity.User;
import com.ansysan.cleverdev.exception.DataValidationException;
import com.ansysan.cleverdev.mapper.NoteMapper;
import com.ansysan.cleverdev.repository.NoteRepository;
import com.ansysan.cleverdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final UserRepository userRepository;

    public NoteDto createNote(NoteDto noteDto) {
        Note note = noteMapper.toEntity(noteDto);
        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    public NoteDto publishNote(long noteId) {
        Note note = existsNote(noteId);
        note.setCreatedDateTime(LocalDateTime.now());
        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    public NoteDto updateNote(long noteId, NoteDto noteDto) {
        Note note = existsNote(noteId);

        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    public NoteDto deleteNote(long noteId) {
        Note note = existsNote(noteId);
        noteRepository.deleteById(noteId);
        return noteMapper.toDto(note);
    }

    public Map<Long, String> showNotes(String login) {
        User user = userRepository.findByLogin(login);
        List<Note> list = user.getListNoteForUserCreated();
        Map<Long, String> map = new HashMap<>();
        for (Note note : list) {
            map.put(note.getId(), note.getNote());
        }
        return map;
    }

    public Note createdNoteFromOldSystem(Patient findIdPatientForWriteForNoteEntity,
                                         User findIdUserForWriteUserEntity,
                                         Map jsonNoteKey) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        NoteDto noteDto;

        Optional<Note> note = noteRepository.findByCreatedDateTime(LocalDateTime.parse((String) jsonNoteKey.get("createdDateTime"), formatter));
        LocalDateTime dateTimeFromOldSystem = LocalDateTime.parse(jsonNoteKey.get("modifiedDateTime").toString(), formatter);
        String commentUpdate = (String) jsonNoteKey.get("comments");

        if (note.isPresent()) {
            if(note.get().getLastModifiedDateTime().equals(dateTimeFromOldSystem)) {
                return note.get();
            }
            if(note.get().getLastModifiedDateTime().isBefore(dateTimeFromOldSystem)) {
                note.get().setLastModifiedDateTime(dateTimeFromOldSystem);
                note.get().setNote(commentUpdate);
                noteRepository.save(note.get());
            }
            if(note.get().getLastModifiedDateTime().isAfter(dateTimeFromOldSystem)) {
                noteRepository.save(note.get());
            }
        }

        noteDto = NoteDto.builder()
                .createdDateTime(LocalDateTime.parse((String) jsonNoteKey.get("createdDateTime"), formatter))
                .lastModifiedDateTime(LocalDateTime.parse(jsonNoteKey.get("modifiedDateTime").toString(), formatter))
                .createdByUserId(findIdUserForWriteUserEntity)
                .lastModifiedByUserId(findIdUserForWriteUserEntity)
                .comment((String) jsonNoteKey.get("comments"))
                .patient(findIdPatientForWriteForNoteEntity)
                .build();
        return noteMapper.toEntity(noteDto);
    }

    Note existsNote(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new DataValidationException("Note with ID " + noteId + " not found"));
    }

}
