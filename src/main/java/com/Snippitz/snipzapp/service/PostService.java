package com.Snippitz.snipzapp.service;

import com.Snippitz.snipzapp.dto.UpdatePostDto;

import com.Snippitz.snipzapp.entity.Post;
import com.Snippitz.snipzapp.entity.SnipUser;
import com.Snippitz.snipzapp.entity.UserLike;
import com.Snippitz.snipzapp.error.ResourceNotFoundException;
import com.Snippitz.snipzapp.repository.LikeRepository;
import com.Snippitz.snipzapp.repository.PostRepository;
import com.Snippitz.snipzapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PostService {
    private final LikeRepository likeRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;
    public PostService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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

    public UserLike sendALike(UUID postId, UUID userId) {

        List<UserLike> list = this.likeRepository.findByPostIdAndUserId(postId,userId);
        if(list.size() > 0){
            throw new ResourceNotFoundException();
        }
        SnipUser snipUser = this.userRepository.findById(userId).get();
        Post post = this.postRepository.findById(postId).get();
        UserLike userLike = new UserLike(new Random().nextLong(),post,snipUser);


        return this.likeRepository.save(userLike);
    }
}
