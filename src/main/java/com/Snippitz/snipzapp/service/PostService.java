package com.Snippitz.snipzapp.service;

import com.Snippitz.snipzapp.dto.UpdatePostDto;
import com.Snippitz.snipzapp.entity.Post;
import com.Snippitz.snipzapp.error.ResourceNotFoundException;
import com.Snippitz.snipzapp.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public Post findPostById(UUID id){
        return this.postRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException("Post not found with id: " + id);});
    }

    public Post createPost(Post post){

        return postRepository.save(post);
    }

    public List<Post> findByLanguage(String type) {
        return postRepository.findByLanguage(type);
    }

    public void likePost(UUID postId) {
        Post p = this.postRepository.findById(postId).get();
        p.setLikes(p.getLikes() + 1);
        this.postRepository.save(p);

    }

    public void deletePost(UUID postid) {
        Post post = this.postRepository.findById(postid).orElseThrow(() -> {throw new ResourceNotFoundException("Post not found:" + postid);});
        this.postRepository.delete(post);
    }

    public Post updatePost(UpdatePostDto updatePostDto,UUID id) {
      Post post =  this.postRepository.findById(id).orElseThrow(() -> {throw new ResourceNotFoundException("Post not found:" + id );});
      post.setCode(updatePostDto.getCode());
      post.setDescription(updatePostDto.getDescription());
     return this.postRepository.save(post);
    }

    public List<Post> getAllEmployeesPageable(Integer pageNo, Integer pageSize, String sortBy) {
        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Post> pagedResult = postRepository.findAll((org.springframework.data.domain.Pageable) paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Post>();
        }
    }
}
