package io.poststead.poststeadpostservice.exception.user_exception;

import io.poststead.poststeadpostservice.exception.ResourceNotFoundException;

public class PostNotFoundException extends ResourceNotFoundException {

    public PostNotFoundException() {
        super("Post was not found!");
    }

    public PostNotFoundException(String username) {
        super("Post by " + username + " was not found!");
    }

}