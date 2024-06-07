package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.InpBookingDto;
import ru.practicum.shareit.booking.dto.OutBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.InternalServerError;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.booking.BookingMapper.toOutBookingDto;
import static ru.practicum.shareit.booking.BookingMapper.toOutBookingDtoLst;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", userId)));
    }

    private Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("Item with id %d not found", itemId)));
    }

    @Override
    @Transactional
    public OutBookingDto addBooking(InpBookingDto bookingDto, Long userId) {
        Long itemId = bookingDto.getItemId();
        Item item = getItemById(itemId);
        User owner = item.getOwner();
        if (owner == null) {
            throw new AccessException(String.format("Item with id = %d not have owner.", itemId));
        }
        if (owner.getId().equals(userId)) {
            throw new AccessException(String.format("Booker cannot be owner of item id: %d", userId));
        }
        LocalDateTime start = bookingDto.getStart();
        LocalDateTime end = bookingDto.getEnd();
        if (end.isBefore(start) || end.equals(start)) {
            throw new ValidationException(String.format("Wrong booking time start = %s and end = %s", start, end));
        }
        if (!item.getAvailable()) {
            throw new ValidationException(String.format("Item with id: %d is not available!", userId));
        }
        Booking booking = Booking.builder()
                .start(start)
                .end(end)
                .item(item)
                .booker(getUserById(userId))
                .status(BookingStatus.WAITING)
                .build();
        return toOutBookingDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public OutBookingDto approveBooking(Long bookingId, Long userId, Boolean approve) {
        Booking booking = getBookingById(bookingId, userId);
        if (booking.getStatus().equals(BookingStatus.APPROVED)) {
            throw new ValidationException(String.format("Booking with id: %d already have status %s",
                    bookingId, BookingStatus.APPROVED));
        }
        checkAccessToBooking(booking, userId, false);
        BookingStatus bookingStatus = approve ? BookingStatus.APPROVED : BookingStatus.REJECTED;
        booking.setStatus(bookingStatus);
        bookingRepository.updateStatus(bookingStatus, bookingId);
        return toOutBookingDto(bookingRepository.save(booking));
    }

    @Override
    public Booking getBookingById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(String.format("Booking with id: %d not found", bookingId)));
        checkAccessToBooking(booking, userId, true);
        return booking;
    }

    private void checkAccessToBooking(Booking booking, Long userId, boolean accessForBooker) {
        User booker = booking.getBooker();
        if (booker == null) {
            throw new InternalServerError(String.format("For booking with id: %s Bouker is not installed!", booker.getId()));
        }
        Long bookerId = booker.getId();
        Item item = booking.getItem();
        if (item == null) {
            throw new InternalServerError(String.format("For booking with id: %s Item is not installed!", booker.getId()));
        }
        User owner = item.getOwner();
        if (owner == null) {
            throw new InternalServerError(String.format("For booking with id: %s Owner is not installed!", booker.getId()));
        }
        Long ownerId = owner.getId();
        if (ownerId.equals(userId)) {
            return;
        }
        if (accessForBooker && bookerId.equals(userId)) {
            return;
        }
        throw new AccessException(String.format("Access to User id:%s for booking id:%s is denied",
                userId, booking.getId()));
    }

    @Override
    public OutBookingDto getBookingDtoById(Long bookingId, Long userId) {
        Booking booking = getBookingById(bookingId, userId);
        return toOutBookingDto(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OutBookingDto> getBookingsOfBooker(BookingState bookingState, Long bookerId) {
        getUserById(bookerId);
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        List<Booking> bookings;
        switch (bookingState) {
            case WAITING:
                bookings = bookingRepository.getAllByBookerIdAndStatus(bookerId, BookingStatus.WAITING, sort);
                break;
            case REJECTED:
                bookings = bookingRepository.getAllByBookerIdAndStatus(bookerId, BookingStatus.REJECTED, sort);
                break;
            case PAST:
                bookings = bookingRepository.getAllByBookerIdAndEndBefore(bookerId, LocalDateTime.now(), sort);
                break;
            case FUTURE:
                bookings = bookingRepository.getAllByBookerIdAndStartAfter(bookerId, LocalDateTime.now(), sort);
                break;
            case CURRENT:
                bookings = bookingRepository.getAllByBkrIdAndStartBeforeAndEndAfter(bookerId, LocalDateTime.now());
                break;
            default:
                bookings = bookingRepository.getAllByBookerId(bookerId, sort);
        }
        return toOutBookingDtoLst(bookings);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OutBookingDto> getBookingsOfOwner(BookingState bookingState, Long ownerId) {
        getUserById(ownerId);
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        List<Booking> bookings;
        switch (bookingState) {
            case WAITING:
                bookings = bookingRepository.findAllByOwnerIdAndStatus(ownerId, BookingStatus.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository.findAllByOwnerIdAndStatus(ownerId, BookingStatus.REJECTED);
                break;
            case PAST:
                bookings = bookingRepository.findAllByOwnerIdAndEndBefore(ownerId, LocalDateTime.now());
                break;
            case FUTURE:
                bookings = bookingRepository.findAllByOwnerIdAndStartAfter(ownerId, LocalDateTime.now());
                break;
            case CURRENT:
                bookings = bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(ownerId, LocalDateTime.now());
                break;
            default:
                bookings = bookingRepository.findAllByOwnerId(ownerId, sort);
        }
        return toOutBookingDtoLst(bookings);
    }
}