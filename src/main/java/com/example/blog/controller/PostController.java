package com.example.blog.controller;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@Validated
@CrossOrigin(origins = "*")
public class PostController {
    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Post> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {
        System.out.println("POST request received: " + post);

        post.setId(null);
        Post savedPost = repository.save(post);
        return ResponseEntity.ok(savedPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @Valid @RequestBody Post newPost) {
        return repository.findById(id).map(post -> {
            post.setTitle(newPost.getTitle());
            post.setContent(newPost.getContent());
            post.setAuthor(newPost.getAuthor());
            post.setImage(newPost.getImage());
            post.setCategory(newPost.getCategory());
            post.setTags(newPost.getTags());
            post.setPostDate(newPost.getPostDate());
            post.setDescription(newPost.getDescription());
            repository.save(post);
            return ResponseEntity.ok(post);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Post> optionalPost = repository.findById(id);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "title" -> post.setTitle((String) value);
                case "content" -> post.setContent((String) value);
                case "author" -> post.setAuthor((String) value);
                case "image" -> post.setImage((String) value);
                case "category" -> post.setCategory((String) value);
                case "tags" -> post.setTags((String) value);
                case "postDate" -> post.setPostDate((Timestamp) value);
                case "description" -> post.setDescription((String) value);
            }
        });
        repository.save(post);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
