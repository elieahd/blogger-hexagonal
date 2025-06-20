package com.devt.blogger.domain.inbound;

import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.domain.exceptions.CategoryNotFoundByIdException;

import java.util.UUID;

public interface CreatePost {

    Post create(String title,
                String content,
                UUID categoryId) throws CategoryNotFoundByIdException;

}
