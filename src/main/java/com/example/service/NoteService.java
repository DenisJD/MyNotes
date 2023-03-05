package com.example.service;

import com.example.dto.NoteDto;
import com.example.model.Note;

public interface NoteService {

    Note createNote(NoteDto noteDto);

    Note updateNote(Long id, NoteDto noteDto);

}
