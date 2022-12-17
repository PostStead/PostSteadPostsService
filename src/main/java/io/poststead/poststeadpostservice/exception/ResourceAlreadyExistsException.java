package io.poststead.poststeadpostservice.exception;

public abstract class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
