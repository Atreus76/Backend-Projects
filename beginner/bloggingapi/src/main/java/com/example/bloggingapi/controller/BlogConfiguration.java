package com.example.bloggingapi.controller;


import com.example.bloggingapi.models.Blog;
import com.example.bloggingapi.repository.BlogRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class BlogConfiguration {
    CommandLineRunner commandLineRunner(BlogRepository repository){
        return args -> {
            Blog firstBlog = new Blog(
                    "First blog",
                    "NO CONTENT",
                    "John Doe",
                    LocalDate.of(2000, Month.JANUARY, 20)
            );
            Blog secondBlog = new Blog(
                    "Second blog",
                    "NO CONTENT",
                    "David Martin",
                    LocalDate.of(2020, Month.JANUARY, 12)
            );

        };
    }
}
