package com.app.todoapp.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private boolean completed;
}
