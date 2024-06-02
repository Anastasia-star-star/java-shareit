package ru.practicum.shareit.exception;

public class NotFoundByRequestException extends RuntimeException {
    public NotFoundByRequestException(String message) {
        super(message);
    }
}
