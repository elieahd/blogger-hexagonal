package com.devt.blogger.domain.inbound;

import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.domain.exceptions.PostNotFoundByIdException;

import java.util.List;
import java.util.UUID;

public interface SearchPosts {

    List<Post> getAll();

    Post getById(UUID id) throws PostNotFoundByIdException;

}
