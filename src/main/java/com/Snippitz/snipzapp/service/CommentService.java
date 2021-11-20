package com.Snippitz.snipzapp.service;

import com.Snippitz.snipzapp.entity.Comment;
import com.Snippitz.snipzapp.entity.Post;
import com.Snippitz.snipzapp.repository.CommentRepository;
import com.Snippitz.snipzapp.repository.PostRepository;
import com.Snippitz.snipzapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public CommentService(UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment postComment(UUID id, Comment comment){
        Post post = this.postRepository.findById(id).get();
        comment.setPost(post);

        return this.commentRepository.save(comment);
    }

    public List<Comment> getComments(UUID id){
        return this.commentRepository.findByPostId( id );
    }

    public List<Comment> getAllComments() {
        return this.commentRepository.findAll();
    }
}
