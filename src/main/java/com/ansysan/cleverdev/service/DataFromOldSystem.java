package com.ansysan.cleverdev.service;

import com.ansysan.cleverdev.entity.Note;
import com.ansysan.cleverdev.entity.Patient;
import com.ansysan.cleverdev.entity.User;
import com.ansysan.cleverdev.repository.NoteRepository;
import com.ansysan.cleverdev.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class DataFromOldSystem {

    private final UserRepository userRepository;
    private final UserService userService;
    private final NoteRepository noteRepository;
    private final NoteService noteService;
    private long countNote;

    public Long saveNoteInDB(JSONArray responseDetailsNotes, Patient patient) {

        for (Object it : responseDetailsNotes) {

            LinkedHashMap<Object, Object> jsonNoteKey = (LinkedHashMap) it;

            User findIdUserForWriteUserEntity = userRepository.findByLogin((String) jsonNoteKey.get("loggedUser"));  // 0
            if (findIdUserForWriteUserEntity == null) {
                userService.saveUserFromOldVersion((String) jsonNoteKey.get("loggedUser"));
                User findIfNotFound = userRepository.findByLogin((String) jsonNoteKey.get("loggedUser"));
                findIdUserForWriteUserEntity = findIfNotFound;
            }

            Note note = noteService.createdNoteFromOldSystem(patient,
                    findIdUserForWriteUserEntity, jsonNoteKey);
            Optional<Note> findData = noteRepository.findByCreatedDateTime(note.getCreatedDateTime());
            if (findData.isEmpty()) {
                noteRepository.save(note);
                countNote++;
            }
        }
        return countNote;
    }

}