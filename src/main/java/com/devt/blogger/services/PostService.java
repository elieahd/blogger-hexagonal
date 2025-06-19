package com.devt.blogger.services;

import com.devt.blogger.exceptions.CategoryNotFoundByIdException;
import com.devt.blogger.exceptions.PostNotFoundByIdException;
import com.devt.blogger.models.Category;
import com.devt.blogger.models.Post;
import com.devt.blogger.repositories.CategoryRepository;
import com.devt.blogger.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository,
                       CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post create(String title,
                       String content,
                       UUID categoryId) throws CategoryNotFoundByIdException {
        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundByIdException(categoryId);
        }
        Post post = new Post(title, content, category);
        postRepository.create(post);
        return post;
    }

    public Post getById(UUID id) throws PostNotFoundByIdException {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new PostNotFoundByIdException(id);
        }
        return post;
    }
}
