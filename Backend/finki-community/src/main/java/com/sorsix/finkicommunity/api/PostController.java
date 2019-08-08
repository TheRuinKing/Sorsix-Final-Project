package com.sorsix.finkicommunity.api;

import com.sorsix.finkicommunity.domain.entities.Post;
import com.sorsix.finkicommunity.domain.entities.User;
import com.sorsix.finkicommunity.domain.requests.errors.NewPostRequest;
import com.sorsix.finkicommunity.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/forum/posts")
@CrossOrigin(origins = "http://localhost:4200")  // Enabling Cross Origin Requests for a RESTful Web Service
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{userId}")
    public ResponseEntity getAllPostsByUserId(@PathVariable Long userId){
        List<Post> result = postService.getAllPostsByUserId(userId);
        if(result == null){
            return ResponseEntity.badRequest().body("There is no user with id = " + userId);
        }
        else{
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<User> createNewPost(@RequestBody @Valid NewPostRequest newPostRequest){
        return ResponseEntity.ok(postService.createNewPost(newPostRequest));

    }
}
