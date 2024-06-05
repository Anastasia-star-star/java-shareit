package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    @Autowired
    private final ItemService itemService;
    private static final String XSHARERUSERID = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item addItem(@RequestHeader(XSHARERUSERID) Integer userId,
                        @Valid @RequestBody ItemDto itemDto) {
        log.info("Adding new item: {}", itemDto);
        return itemService.addItem(userId, itemDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getItemOfUserId(@RequestHeader(XSHARERUSERID) Integer userId) {
        log.info("Getting item by user id: {}", userId);
        return itemService.getItemOfUserId(userId);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Item> getItemById(@PathVariable Integer itemId) {
        log.info("Getting item by id: {}", itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Item> searchItem(@RequestParam String text) {
        log.info("Searching item by text: {}", text);
        return itemService.searchItem(text);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Item update(@RequestHeader(XSHARERUSERID) Integer userId,
                       @PathVariable Integer itemId,
                       @RequestBody ItemDto itemDto) {
        log.info("Updating item of user by item id: {}", itemId);
        return itemService.update(userId, itemId, itemDto);
    }
}
