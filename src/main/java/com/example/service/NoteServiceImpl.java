package com.example.service;

import com.example.dto.NoteDto;
import com.example.model.Note;
import com.example.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public Note createNote(final NoteDto noteDto) {
        final Note note = new Note();
        note.setHeader(noteDto.getHeader());
        note.setDescription(noteDto.getDescription());
        noteRepository.save(note);
        return note;
    }

    @Override
    public Note updateNote(final Long id, final NoteDto noteDto) {
        final Note noteToUpdate = noteRepository.findById(id).get();
        noteToUpdate.setHeader(noteDto.getHeader());
        noteToUpdate.setDescription(noteDto.getDescription());
        noteRepository.save(noteToUpdate);
        return noteToUpdate;
    }
}
