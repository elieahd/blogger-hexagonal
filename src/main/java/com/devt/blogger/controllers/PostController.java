package com.devt.blogger.controllers;

import com.devt.blogger.dtos.PostRequest;
import com.devt.blogger.exceptions.CategoryNotFoundByIdException;
import com.devt.blogger.exceptions.PostNotFoundByIdException;
import com.devt.blogger.models.Post;
import com.devt.blogger.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postService.getPosts();
        return ok(posts);
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) throws PostNotFoundByIdException {
        Post post = postService.getById(id);
        return ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody PostRequest postRequest) throws CategoryNotFoundByIdException {
        Post post = postService.create(
                postRequest.title(),
                postRequest.content(),
                postRequest.categoryId()
        );
        return ResponseEntity
                .created(URI.create("v1/posts/" + post.getId()))
                .body(post);
    }
}
