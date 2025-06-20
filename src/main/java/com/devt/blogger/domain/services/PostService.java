package com.devt.blogger.domain.services;

import com.devt.blogger.domain.entities.Category;
import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.domain.exceptions.CategoryNotFoundByIdException;
import com.devt.blogger.domain.exceptions.PostNotFoundByIdException;
import com.devt.blogger.domain.inbound.CreatePost;
import com.devt.blogger.domain.inbound.SearchPosts;
import com.devt.blogger.domain.outbound.CategoryInventory;
import com.devt.blogger.domain.outbound.PostInventory;

import java.util.List;
import java.util.UUID;

@DomainService
public class PostService implements SearchPosts, CreatePost {

    private final PostInventory postInventory;
    private final CategoryInventory categoryInventory;

    public PostService(PostInventory postInventory,
                       CategoryInventory categoryInventory) {
        this.postInventory = postInventory;
        this.categoryInventory = categoryInventory;
    }

    @Override
    public Post create(String title,
                       String content,
                       UUID categoryId) throws CategoryNotFoundByIdException {
        Category category = categoryInventory.findById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundByIdException(categoryId);
        }
        Post post = new Post(title, content, category);
        postInventory.create(post);
        return post;
    }

    @Override
    public List<Post> getAll() {
        return postInventory.findAll();
    }

    @Override
    public Post getById(UUID id) throws PostNotFoundByIdException {
        Post post = postInventory.findById(id);
        if (post == null) {
            throw new PostNotFoundByIdException(id);
        }
        return post;
    }
}
