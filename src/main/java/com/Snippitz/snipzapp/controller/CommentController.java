package com.Snippitz.snipzapp.controller;

import com.Snippitz.snipzapp.entity.Comment;
import com.Snippitz.snipzapp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/comments")
    public List<Comment> getAllComments(){
        return this.commentService.getAllComments();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/api/comments/{postId}")
    public List<Comment> getAllComments(@PathVariable UUID postId){
        System.out.println(postId);

        return commentService.getComments(postId);
    }


    @PostMapping("/api/posts/{postId}/comments")
    public Comment postComment( @PathVariable UUID postId,@RequestBody Comment comment){
        commentService.postComment(postId,comment);
        return comment;
    }
}
