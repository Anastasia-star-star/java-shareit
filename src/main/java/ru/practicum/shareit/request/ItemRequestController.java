package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoOut;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static ru.practicum.shareit.item.ItemController.X_SHARER_USER_ID;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {

    private final ItemRequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestDtoOut add(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                 @Valid @RequestBody ItemRequestDto requestDto) {
        log.info("Добавление запроса");
        return requestService.add(userId, requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestDtoOut> getUserRequests(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("Получение пользователя");
        return requestService.getUserRequests(userId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestDtoOut> getAllRequests(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                  @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
                                                  @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size) {
        log.info("Получение всех запросов");
        return requestService.getAllRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemRequestDtoOut getRequestById(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                 @PathVariable Long requestId) {
        log.info("Получение запроса по Id");
        return requestService.getRequestById(userId, requestId);
    }
}

