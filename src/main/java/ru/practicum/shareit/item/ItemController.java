package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader(X_SHARER_USER_ID) long ownerId,
                          @Valid @RequestBody @NotNull ItemDto itemDto) {
        log.info("Adding new item");
        return itemService.addItem(ownerId, ItemMapper.toItem(itemDto));
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(X_SHARER_USER_ID) long ownerId, @PathVariable long itemId,
                          @RequestBody @NotNull Map<String, String> updates) {
        log.info("Updating item");
        return itemService.update(ownerId, itemId, updates);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemDtoById(@RequestHeader(X_SHARER_USER_ID) long userId,
                           @PathVariable long itemId) {
        log.info("Getting item DTO by Id");
        return itemService.getItemDtoById(itemId, userId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping
    public List<ItemDto> getAllUserItems(@RequestHeader(X_SHARER_USER_ID) long ownerId) {
        log.info("Getting all users items");
        return itemService.getAllUserItems(ownerId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestHeader(X_SHARER_USER_ID) long userId,
                                     @RequestParam(name = "text") String text) {
        log.info("Searching item");
        return itemService.searchItems(userId, text);
    }

    @ResponseStatus(HttpStatus.GONE)
    @DeleteMapping("/{itemId}")
    public void delete(@RequestHeader(X_SHARER_USER_ID) long ownerId,
                       @PathVariable long itemId) {
        log.info("Deleting item");
        itemService.delete(ownerId, itemId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(X_SHARER_USER_ID) long userId,
                                 @PathVariable long itemId,
                                 @Valid @RequestBody CommentDto commentDto) {
        log.info("Adding comment");
        return itemService.addComment(userId, itemId, commentDto);
    }
}
