package com.example.controller;

import com.example.dto.NoteDto;
import com.example.model.Note;
import com.example.repository.NoteRepository;
import com.example.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.example.controller.NoteController.NOTES_CONTROLLER_PATH;

@AllArgsConstructor
@Controller
@RequestMapping("${base-url}" + NOTES_CONTROLLER_PATH)
public class NoteController {

    public static final String NOTES_CONTROLLER_PATH = "/notes";

    public static final String ID = "/{id}";

    public static final String EDIT = "/edit";

    public static final String NEW = "/new";

    private static final int PAGE_SIZE = 9;

    private final NoteRepository noteRepository;

    private final NoteService noteService;

    @GetMapping
    public String getNotes(@PageableDefault(size = PAGE_SIZE) Pageable pageable,
                           Model model) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Note> page = noteRepository.findByOrderByIdDesc(pageRequest);
        model.addAttribute("page", page);
        return "notes";
    }

    @GetMapping(ID)
    public String getNote(@PathVariable final Long id,
                          Model model) {
        if (noteRepository.existsById(id)) {
            Note note = noteRepository.findById(id).get();
            model.addAttribute("note", note);
            return "note";
        }
        return "redirect:/api";
    }

    @GetMapping(NEW)
    public String newNote(Model model) {
        model.addAttribute("newNote", new Note());
        return "new";
    }

    @PostMapping(NEW)
    public String createNote(@ModelAttribute("newNote") @Valid final NoteDto noteDto) {
        noteService.createNote(noteDto);
        return "redirect:/api/notes";
    }

    @GetMapping(ID + EDIT)
    public String editNote(@PathVariable final Long id,
                           Model model) {
        if (noteRepository.existsById(id)) {
            Note note = noteRepository.findById(id).get();
            model.addAttribute("noteToUpdate", note);
            return "edit";
        }
        return "redirect:/api";
    }

    @PutMapping(ID + EDIT)
    public String updateNote(@PathVariable final Long id,
                             @ModelAttribute("noteToUpdate") @Valid final NoteDto noteDto) {
        if (noteRepository.existsById(id)) {
            noteService.updateNote(id, noteDto);
            return "redirect:/api/notes/" + id;
        }
        return "redirect:/api";
    }

    @DeleteMapping(ID)
    public String deleteNote(@PathVariable final Long id) {
        noteRepository.deleteById(id);
        return "redirect:/api/notes";
    }
}
