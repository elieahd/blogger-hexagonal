package com.devt.blogger.domain.outbound;

import com.devt.blogger.domain.entities.Post;

import java.util.List;
import java.util.UUID;

public interface PostInventory {

    List<Post> findAll();

    Post findById(UUID postId);

    void create(Post post);

}
