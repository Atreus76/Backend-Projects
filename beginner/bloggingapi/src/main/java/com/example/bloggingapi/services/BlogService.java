package com.example.bloggingapi.services;

import com.example.bloggingapi.models.Blog;
import com.example.bloggingapi.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
@Service
public class BlogService {
    private final BlogRepository blogRepository;
    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> getBlogs(){
        return blogRepository.findAll();
    }


    public void addNewBlog(Blog blog) {
        System.out.println(blog);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public void createBlog(Blog blog) {
        blog.setCreatedAt(LocalDate.from(LocalDateTime.now()));
        blogRepository.save(blog);
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    public void updateBlog(Long id, Blog blog) {
        Blog existing = getBlogById(id);
        existing.setTitle(blog.getTitle());
        existing.setContent(blog.getContent());
        blogRepository.save(existing);
    }
}
