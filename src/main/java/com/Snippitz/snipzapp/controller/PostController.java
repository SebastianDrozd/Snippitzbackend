package com.Snippitz.snipzapp.controller;

import com.Snippitz.snipzapp.entity.Language;
import com.Snippitz.snipzapp.entity.Post;
import com.Snippitz.snipzapp.service.LanguageService;
import com.Snippitz.snipzapp.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    private final LanguageService languageService;

    public PostController(PostService postService, LanguageService languageService) {
        this.postService = postService;
        this.languageService = languageService;
    }

    @GetMapping("/api/posts/{postId}/like")
    public void sendALike(@PathVariable UUID postId){
        this.postService.likePost(postId);
    }




    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(this.postService.getAllPosts());
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable UUID id){
        Post post = this.postService.findPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/api/language")
    public ResponseEntity<List<Language>> getAllLanguages(){
        return ResponseEntity.ok(languageService.getAllLanguages());
    }
    @GetMapping("/api/posts/search/{type}")
    public ResponseEntity<List<Post>> getPostsByType(@PathVariable String type){
        List<Post> posts = postService.findByType(type);
        System.out.println(posts);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        System.out.println("got " + post);
   // Post postToSave = Post.builder().description("this is a post").title("this is a title").language(Language.JAVA).createdAt(new Date()).build();
    // Post post= this.postService.createPost(postToSave);
   // return ResponseEntity.ok(post);
        this.postService.createPost(post);
        return ResponseEntity.ok(post);
    }
}
