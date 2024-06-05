package ru.practicum.shareit.exception;

public class RequestWithoutValueException extends RuntimeException {
    public RequestWithoutValueException(String message) {
        super(message);
    }
}
