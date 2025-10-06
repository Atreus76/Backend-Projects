package com.example.markdown_note_taking.controller;

import com.example.markdown_note_taking.models.Note;
import com.example.markdown_note_taking.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/save")
    public ResponseEntity<Note> saveNote(@RequestParam String title, @RequestParam String content) {
        Note savedNote = noteService.saveNote(title, content);
        return ResponseEntity.ok(savedNote);
    }

    @PostMapping("/upload")
    public ResponseEntity<Note> uploadNote(@RequestParam String title, @RequestParam("file") MultipartFile file) throws IOException {
        Note savedNote = noteService.saveNoteFromFile(title, file);
        return ResponseEntity.ok(savedNote);
    }

    @GetMapping("/{id}/grammar")
    public ResponseEntity<String> checkGrammar(@PathVariable Long id) {
        String grammarResult = noteService.checkGrammar(id);
        return ResponseEntity.ok(grammarResult);
    }

    @GetMapping("/{id}/render")
    public ResponseEntity<String> renderMarkdown(@PathVariable Long id) {
        String htmlContent = noteService.renderMarkdown(id);
        return ResponseEntity.ok(htmlContent);
    }
}
