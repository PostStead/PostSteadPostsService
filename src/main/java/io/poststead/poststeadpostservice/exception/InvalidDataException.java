package io.poststead.poststeadpostservice.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {
        super("Invalid data. Check given fields!");
    }

    public InvalidDataException(String field) {
        super("Invalid data. Check " + field + " field!");
    }
}
