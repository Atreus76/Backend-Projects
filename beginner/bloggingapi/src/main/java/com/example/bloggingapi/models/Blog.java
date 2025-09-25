package com.example.bloggingapi.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Blog {
    @Id
    @SequenceGenerator(
            name = "blog_sequence",
            sequenceName = "blog_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "blog_sequence"
    )
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDate createdAt;

    public Blog() {
    }

    public Blog(Long id,
                String title,
                String content,
                String author,
                LocalDate createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

    public Blog(String title,
                String content,
                String author,
                LocalDate createdAt) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public void addAttribute(String message, String s) {

    }
}
