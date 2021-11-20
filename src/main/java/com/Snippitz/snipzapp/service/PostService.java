package com.Snippitz.snipzapp.service;

import com.Snippitz.snipzapp.entity.Post;
import com.Snippitz.snipzapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts(){
        return postRepository.findAllByOrderByCreatedAtDesc();
    }
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public List<Post> findByType(String type) {
        return postRepository.findByLanguage(type);
    }

    public Post findPostById(UUID id){
       return this.postRepository.findById(id).get();
    }

    public void likePost(UUID postId) {
        Post p = this.postRepository.findById(postId).get();
        p.setLikes(p.getLikes() + 1);
        this.postRepository.save(p);

    }
}
