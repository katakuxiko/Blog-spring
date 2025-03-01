package com.example.blog.controller;

import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@Validated
@CrossOrigin(origins = "*")
public class CommentController {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        if (!postRepository.existsById(postId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentRepository.findByPostId(postId));
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @Valid @RequestBody Comment comment) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        comment.setPost(postOptional.get());
        return ResponseEntity.ok(commentRepository.save(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @Valid @RequestBody Comment newComment) {
        return commentRepository.findById(id).map(comment -> {
            comment.setAuthor(newComment.getAuthor());
            comment.setContent(newComment.getContent());
            return ResponseEntity.ok(commentRepository.save(comment));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (!commentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        commentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}