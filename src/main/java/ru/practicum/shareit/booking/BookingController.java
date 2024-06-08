package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@Slf4j
public class BookingController {
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OutBookingDto addBooking(@RequestHeader(X_SHARER_USER_ID) long userId,
                                    @Valid @RequestBody InpBookingDto booking) {
        log.info("Adding new booking");
        return bookingService.addBooking(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public OutBookingDto approveBooking(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                @PathVariable Long bookingId,
                                @RequestParam boolean approved) {
        log.info("Updating booking");
        return bookingService.approveBooking(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public OutBookingDto getBookingDtoById(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                 @PathVariable Long bookingId) {
        log.info("Getting booking");
        return bookingService.getBookingDtoById(bookingId, userId);
    }

    @GetMapping
    public List<OutBookingDto> getBookingsOfUser(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                 @RequestParam(defaultValue = "ALL") String state) {
        log.info("Getting booking of user");
        return bookingService.getBookingsOfBooker(BookingState.getState(state), userId);
    }

    @GetMapping("/owner")
    public List<OutBookingDto> getBookingsOfOwner(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                  @RequestParam(defaultValue = "ALL") String state) {
        log.info("Getting booking of Owner");
        return bookingService.getBookingsOfOwner(BookingState.getState(state), userId);
    }
}
