package ru.practicum.shareit.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRequestWithoutValueException(final RequestWithoutValueException exception) {
        log.info("Missed value by request");
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException exception) {
        log.info("Value not found");
        return new ErrorResponse(exception.getMessage());
    }

    @Getter // геттеры необходимы, чтобы Spring Boot мог получить значения полей
    public class ErrorResponse {
        // название ошибки
        private String error;
        private String description;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public ErrorResponse(String error, String description) {
            this.error = error;
            this.description = description;
        }
    }
}
