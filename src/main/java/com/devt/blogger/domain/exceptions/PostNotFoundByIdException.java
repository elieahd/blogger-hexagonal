package com.devt.blogger.domain.exceptions;

import java.util.UUID;

public class PostNotFoundByIdException extends Exception {

    public PostNotFoundByIdException(UUID id) {
        super("Post with id " + id + " not found");
    }

}