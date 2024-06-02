package ru.practicum.shareit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFoundByRequestException(final NotFoundByRequestException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @Getter // геттеры необходимы, чтобы Spring Boot мог получить значения полей
    public class ErrorResponse {
        // название ошибки
        String error;
        String description;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public ErrorResponse(String error, String description) {
            this.error = error;
            this.description = description;
        }
    }
}
