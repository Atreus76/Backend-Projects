package com.example.markdown_note_taking.repository;

import com.example.markdown_note_taking.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
