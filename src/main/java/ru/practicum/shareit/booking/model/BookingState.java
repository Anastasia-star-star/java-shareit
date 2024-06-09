package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.exception.ArgumentException;

public enum BookingState {
    WAITING,
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    REJECTED;

    public static BookingState getState(String text) {
        if ((text == null) || text.isBlank()) {
            return BookingState.ALL;
        }

        try {
            return BookingState.valueOf(text.toUpperCase().trim());
        } catch (Exception e) {
            throw new ArgumentException(String.format("Unknown state: %s", text));
        }
    }
}