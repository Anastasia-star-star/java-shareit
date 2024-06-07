package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.InpBookingDto;
import ru.practicum.shareit.booking.dto.OutBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;

import java.util.List;

public interface BookingService {

    OutBookingDto addBooking(InpBookingDto bookingDto, Long userId);

    OutBookingDto approveBooking(Long bookingId, Long userId, Boolean approve);

    Booking getBookingById(Long bookingId, Long userId);

    OutBookingDto getBookingDtoById(Long bookingId, Long userId);

    List<OutBookingDto> getBookingsOfBooker(State state, Long bookerId);

    List<OutBookingDto> getBookingsOfOwner(State state, Long ownerId);
}
