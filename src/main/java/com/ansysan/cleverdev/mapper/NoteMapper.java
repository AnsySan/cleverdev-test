package com.ansysan.cleverdev.mapper;

import com.ansysan.cleverdev.dto.NoteDto;
import com.ansysan.cleverdev.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoteMapper {

    Note toEntity(NoteDto noteDto);

    NoteDto toDto(Note note);
}