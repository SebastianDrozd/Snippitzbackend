package com.Snippitz.snipzapp.controller;
import com.Snippitz.snipzapp.dto.DeleteUserLikeDto;
import com.Snippitz.snipzapp.dto.ReadPostDto;
import com.Snippitz.snipzapp.dto.UpdatePostDto;
import com.Snippitz.snipzapp.dto.UserLikeDto;
import com.Snippitz.snipzapp.entity.UserLike;
import com.Snippitz.snipzapp.entity.Post;
import com.Snippitz.snipzapp.helpers.JwtHelper;
import com.Snippitz.snipzapp.service.LanguageService;
import com.Snippitz.snipzapp.service.PostService;
import com.Snippitz.snipzapp.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class PostController {
    private final UserService userService;
    private final PostService postService;
    private final LanguageService languageService;
    private final JwtHelper jwtHelper;
    public PostController(UserService userService, PostService postService, LanguageService languageService, JwtHelper jwtHelper) {
        this.userService = userService;
        this.postService = postService;
        this.languageService = languageService;
        this.jwtHelper = jwtHelper;
    }

    //get all posts
    @GetMapping("/api/posts")
    public ResponseEntity<List<ReadPostDto>> getAllPosts(){
        List<ReadPostDto> readPostDtos = new ArrayList<>();
        List<Post> posts = this.postService.getAllPosts();
        for(Post post : posts){
            readPostDtos.add(ReadPostDto.builder()
                    .language(post.getLanguage())
                    .code(post.getCode())
                    .title(post.getTitle())
                    .author(post.getAuthor())
                    .id(post.getId())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .description(post.getDescription())
                    .likes(post.getLikes()).build());
        }
        return ResponseEntity.ok(readPostDtos);
    }

    //get post by id
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<ReadPostDto> getPost(@PathVariable UUID id){
        Post post = this.postService.findPostById(id);
        ReadPostDto readPostDto = ReadPostDto
                .builder()
                .language(post.getLanguage())
                .code(post.getCode())
                .title(post.getTitle())
                .author(post.getAuthor())
                .id(post.getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .description(post.getDescription())
                .likes(post.getLikes())
                .build();
        return ResponseEntity.ok(readPostDto);
    }

    //get posts by language
    @GetMapping("/api/posts/search/{type}")
    public ResponseEntity<List<ReadPostDto>> getPostsByLanguage(@PathVariable String type){
        List<Post> posts = postService.findByLanguage(type);
        List<ReadPostDto> readPostDtos = new ArrayList<>();
        for(Post post : posts){
            readPostDtos.add(ReadPostDto.builder()
                    .language(post.getLanguage())
                    .code(post.getCode())
                    .title(post.getTitle())
                    .author(post.getAuthor())
                    .id(post.getId())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .description(post.getDescription())
                    .likes(post.getLikes()).build());
        }
        System.out.println(posts);
        return ResponseEntity.ok(readPostDtos);
    }
    //send a like to a post
    @GetMapping("/api/posts/{postId}/like")
    public void sendALike(@PathVariable UUID postId){
        this.postService.likePost(postId);
    }

    //save a post
    @PostMapping("/api/posts")
    public ResponseEntity<ReadPostDto> createPost(@RequestBody Post post, @RequestHeader HttpHeaders httpHeaders){
        //read the authorization headers
        String token = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        //set empty username varbale
        String snipUsername = "";
        String realToken = token.substring(7);
        //verify the jwt
        snipUsername = jwtHelper.verifyJwtToken(realToken);
        if(snipUsername.equals("")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Post postEntity = this.postService.createPost(post);
        ReadPostDto readPostDto = ReadPostDto
                .builder()
                .language(post.getLanguage())
                .code(post.getCode())
                .title(post.getTitle())
                .author(post.getAuthor())
                .id(post.getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .description(post.getDescription())
                .likes(post.getLikes())
                .build();

        return ResponseEntity.ok(readPostDto);
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<ReadPostDto> updatedPost(@PathVariable UUID postId,@RequestBody UpdatePostDto updatePostDto){
       Post post = this.postService.updatePost(updatePostDto,postId);
        ReadPostDto readPostDto = ReadPostDto
                .builder()
                .language(post.getLanguage())
                .code(post.getCode())
                .title(post.getTitle())
                .author(post.getAuthor())
                .id(post.getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .description(post.getDescription())
                .likes(post.getLikes())
                .build();
        return ResponseEntity.ok(readPostDto);
    }

    @DeleteMapping("/api/posts/{postId}")
    public void deletePost(@PathVariable UUID postId){
        this.postService.deletePost(postId);
    }


    @GetMapping("/api/posts/query")
    public ResponseEntity<List<Post>> getAllPostsPageable(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy)
    {
        List<Post> list = this.postService.getAllEmployeesPageable(pageNo, pageSize, sortBy);

        return new ResponseEntity<List<Post>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/api/posts/{postId}")
    public ResponseEntity<?> likePost(@PathVariable UUID postId, @RequestBody UserLikeDto userLikeDto){
        UserLike userLike = this.postService.sendALike(postId,UUID.fromString(userLikeDto.getSnipUser()));
        return ResponseEntity.ok(userLike);
    }
    @DeleteMapping("/api/posts/likes/{postId}")
    public void deleteLikePost(@PathVariable UUID postId, DeleteUserLikeDto deleteUserLikeDto){

    }


}
