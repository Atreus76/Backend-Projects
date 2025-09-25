package com.example.bloggingapi.controller;

import com.example.bloggingapi.models.Blog;
import com.example.bloggingapi.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
@Controller
//@RequestMapping (path = "/api/v1/student")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping()
    public String getBlogs(Model model){
        List<Blog> blogs = blogService.getBlogs();
        model.addAttribute("blogs", blogs);
        model.addAttribute("blog",new Blog());
        return "index";  // looks for templates/index.html
    }

    @PostMapping("/blogs")
    public String registerNewBlog(@ModelAttribute Blog blog){
        blogService.createBlog(blog);
        return "redirect:/";
    }

    // Show form for editing an existing blog
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlogById(id); // fetch from DB
        model.addAttribute("blog", blog);
        return "edits"; // templates/edits.html
    }

    // Handle form submission for updating
    @PostMapping("/update/{id}")
    public String updateBlog(@PathVariable Long id, @ModelAttribute Blog blog) {
        blogService.updateBlog(id, blog);
        return "redirect:/"; // back to homepage
    }

    @GetMapping("/read/{id}")
    public String readBlog(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "blog";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return "redirect:/";
    }
}
