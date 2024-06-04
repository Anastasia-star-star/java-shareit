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
    public ErrorResponse handleBadRequestException(final NotFoundByRequestException exception) {
        log.error("Not found by request exception");
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException exception) {
        log.error("Not found exception");
        return new ErrorResponse(exception.getMessage());
    }

    @Getter
    public class ErrorResponse {

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
