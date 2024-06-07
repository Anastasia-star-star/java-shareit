package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.exception.ArgumentException;

import javax.xml.bind.ValidationException;

public enum State {
    WAITING,
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    REJECTED;

    public static State getState(String text) {
        if ((text == null) || text.isBlank()) {
            return State.ALL;
        }
        try {
            return State.valueOf(text.toUpperCase().trim());
        } catch (Exception e) {
            throw new ArgumentException(String.format("Unknown state: %s", text));
        }
    }
}