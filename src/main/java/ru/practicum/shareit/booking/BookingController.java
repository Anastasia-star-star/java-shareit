package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.InpBookingDto;
import ru.practicum.shareit.booking.dto.OutBookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @PostMapping
    public OutBookingDto addBooking(@RequestHeader(X_SHARER_USER_ID) long userId,
                                    @Valid @RequestBody InpBookingDto booking) {
        return bookingService.addBooking(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public OutBookingDto update(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                @PathVariable Long bookingId,
                                @RequestParam boolean approved) {
        return bookingService.approveBooking(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public OutBookingDto getById(@RequestHeader(X_SHARER_USER_ID) Long userId, @PathVariable Long bookingId) {
        return bookingService.getBookingDtoById(bookingId, userId);
    }

    @GetMapping
    public List<OutBookingDto> getBookingsOfUser(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                 @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getBookingsOfBooker(BookingState.getState(state), userId);
    }

    @GetMapping("/owner")
    public List<OutBookingDto> getBookingsOfOwner(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                  @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getBookingsOfOwner(BookingState.getState(state), userId);
    }
}
