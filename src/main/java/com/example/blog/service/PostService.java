package com.example.blog.service;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    @Cacheable(value = "posts", key = "#id")
    public Post getPostById(Long id) {
        return postRepository.findWithTagsById(id)
                .map(post -> {
                    post.getTags().size(); // Инициализируем tags
                    return post;
                })
                .orElse(null);
    }



}

