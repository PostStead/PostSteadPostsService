package io.poststead.poststeadpostservice.exception.advice;

import io.poststead.poststeadpostservice.exception.InvalidDataException;
import io.poststead.poststeadpostservice.exception.ResourceAlreadyExistsException;
import io.poststead.poststeadpostservice.exception.ResourceNotFoundException;
import io.poststead.poststeadpostservice.exception.user_exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final RabbitTemplate rabbitTemplate;

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleException(ResourceNotFoundException exception) {
        sendRabbitMessage(exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = {ResourceAlreadyExistsException.class})
    protected ResponseEntity<Object> handleException(ResourceAlreadyExistsException exception) {
        sendRabbitMessage(exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = {InvalidDataException.class})
    protected ResponseEntity<Object> handleException(InvalidDataException exception) {
        sendRabbitMessage(exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    protected ResponseEntity<Object> handleException(UserNotFoundException exception) {
        sendRabbitMessage(exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(exception.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(Exception exception) {
        sendRabbitMessage(exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Internal server error!\n" + exception.getMessage());
    }

    private void sendRabbitMessage(String event) {
        rabbitTemplate.convertAndSend("io.poststead.exchange", "io.poststead.audit", event);
    }

}
