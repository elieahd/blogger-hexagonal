package com.devt.blogger.domain.stubs;

import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.domain.outbound.PostInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostInMemoryInventory implements PostInventory {

    private final List<Post> posts;

    public PostInMemoryInventory() {
        this.posts = new ArrayList<>();
    }

    @Override
    public List<Post> findAll() {
        return posts;
    }

    @Override
    public Post findById(UUID postId) {
        return posts.stream()
                .filter(p -> p.getId().equals(postId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(Post post) {
        if (post.getId() == null) {
            post.setId(UUID.randomUUID());
        }
        posts.add(post);
    }
}
