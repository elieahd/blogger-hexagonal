package com.devt.blogger.infrastructure.outbound.database;

import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.domain.outbound.PostInventory;
import com.devt.blogger.infrastructure.outbound.OutboundAdapter;
import com.devt.blogger.infrastructure.outbound.database.dao.PostDao;

import java.util.List;
import java.util.UUID;

@OutboundAdapter
public class PostDatabaseInventory implements PostInventory {

    private final PostDao dao;

    public PostDatabaseInventory(PostDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Post> findAll() {
        return dao.findAll();
    }

    @Override
    public Post findById(UUID postId) {
        return dao.findById(postId);
    }

    @Override
    public void create(Post post) {
        dao.create(post);
    }

}
