package com.example.urlshortener.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(
        name = "urls",
        indexes = {
                @Index(name = "idx_short", columnList = "shortUrl")
        }
)
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String longUrl;
    private String shortUrl;
    private LocalDate createAt;
}
