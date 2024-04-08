package com.example.Homework_Eight.controller;

import com.example.Homework_Eight.model.PersonalNote;
import com.example.Homework_Eight.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;


    @PostMapping("/add")
    public ResponseEntity<PersonalNote> addNote(@RequestBody PersonalNote note){
        return ResponseEntity.ok(noteService.addNote(note));
    }


    @GetMapping
    public ResponseEntity<List<PersonalNote>> getAll() {
        return new ResponseEntity<>(noteService.getAllNotes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Optional<PersonalNote>>> getNoteById(@PathVariable("id") Long id) {
        Optional<PersonalNote> noteById = noteService.getNoteById(id);
        if (noteById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.of(Optional.of(new PersonalNote())));
        }
        Optional<PersonalNote> note = noteService.getNoteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(note));
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<PersonalNote> updateNote(@PathVariable Long id, @RequestBody PersonalNote note) {
        try {
            return ResponseEntity.ok(noteService.updateNote(id, note));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        try {
            noteService.deleteNote(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
