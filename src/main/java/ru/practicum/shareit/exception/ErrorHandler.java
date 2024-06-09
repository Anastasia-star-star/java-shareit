package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("ru.practicum.shareit")
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException e) {
        String strError = String.format("Validation Error: %s", e.getMessage());
        log.error(strError);
        return new ErrorResponse(strError);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String strError = e.getMessage();
        String strSubString = "default message";
        int index = strError.lastIndexOf(strSubString);
        String strMessage = index == 0 ? "" : strError.substring(index + strSubString.length());
        log.error(strMessage);
        return new ErrorResponse(strError);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        String strError = String.format("Required object wasn't found: %s", e.getMessage());
        log.error(strError);
        return new ErrorResponse(strError);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleAccessException(AccessException e) {
        String strError = String.format("Access denied: %s", e.getMessage());
        log.error(strError);
        return new ErrorResponse(strError);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleArgumentException(ArgumentException e) {
        String strError = String.format("Bad Request: %s", e.getMessage());
        log.error(strError);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable e) {
        String strError = String.format("Exception has occurred: %s", e.getMessage());
        log.error(strError);
        return new ErrorResponse(strError);
    }
}
