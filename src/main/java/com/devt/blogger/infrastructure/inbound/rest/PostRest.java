package com.devt.blogger.infrastructure.inbound.rest;

import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.domain.exceptions.CategoryNotFoundByIdException;
import com.devt.blogger.domain.exceptions.PostNotFoundByIdException;
import com.devt.blogger.domain.inbound.CreatePost;
import com.devt.blogger.domain.inbound.SearchPosts;
import com.devt.blogger.infrastructure.inbound.rest.dto.PostRequest;
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
public class PostRest {

    private final SearchPosts searchPosts;
    private final CreatePost createPost;

    public PostRest(SearchPosts searchPosts,
                    CreatePost createPost) {
        this.searchPosts = searchPosts;
        this.createPost = createPost;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = searchPosts.getAll();
        return ok(posts);
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) throws PostNotFoundByIdException {
        Post post = searchPosts.getById(id);
        return ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody PostRequest postRequest) throws CategoryNotFoundByIdException {
        Post post = createPost.create(
                postRequest.title(),
                postRequest.content(),
                postRequest.categoryId()
        );
        return ResponseEntity
                .created(URI.create("v1/posts/" + post.getId()))
                .body(post);
    }
}
