package com.devt.blogger.exceptions;

import java.util.UUID;

public class PostNotFoundByIdException extends Exception {

    public PostNotFoundByIdException(UUID id) {
        super("Post with id " + id + " not found");
    }

}