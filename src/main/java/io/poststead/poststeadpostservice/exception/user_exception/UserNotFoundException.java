package io.poststead.poststeadpostservice.exception.user_exception;

import io.poststead.poststeadpostservice.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String username) {
        super("User, " + username + ", was not found!");
    }

}
