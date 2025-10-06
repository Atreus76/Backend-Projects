package com.example.markdown_note_taking.service;

import com.example.markdown_note_taking.models.Note;
import com.example.markdown_note_taking.repository.NoteRepository;
import com.example.markdown_note_taking.utils.GrammarChecker;
import com.example.markdown_note_taking.utils.MarkdownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
    public NoteService(){
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }
    public Note saveNote(String title, String content){
        String uniqueFileName = UUID.randomUUID().toString() + ".md";
        Note note = new Note(title, content, uniqueFileName);
        return noteRepository.save(note);
    }
    public Note saveNoteFromFile(String title, MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetLocation = fileStorageLocation.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation);
        String content = new String(file.getBytes());
        Note note = new Note(title, content, uniqueFileName);
        return noteRepository.save(note);
    }

    public String checkGrammar(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        return GrammarChecker.checkGrammar(note.getContent());
    }

    public String renderMarkdown(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        return MarkdownProcessor.markdownToHtml(note.getContent());
    }

}
