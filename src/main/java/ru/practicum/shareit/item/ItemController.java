package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ItemService itemService;

    private final String XSharerUserId = "X-Sharer-User-Id";

    @PostMapping
    public Item addItem(@RequestHeader(XSharerUserId) Integer userId,
                        @Valid @RequestBody ItemDto itemDto) {
        log.info("Adding new item: {}", itemDto);
        return itemService.addItem(userId, itemDto);
    }

    @GetMapping
    public List<Item> getItemOfUserId(@RequestHeader(XSharerUserId) Integer userId) {
        log.info("Getting item by user id: {}", userId);
        return itemService.getItemOfUserId(userId);
    }

    @GetMapping("/{itemId}")
    public Optional<Item> getItemById(@PathVariable Integer itemId) {
        log.info("Getting item by id: {}", itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping("/search")
    public List<Item> searchItem(@RequestParam String text) {
        log.info("Searching item by text: {}", text);
        return itemService.searchItem(text);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestHeader(XSharerUserId) Integer userId,
                       @PathVariable Integer itemId,
                       @RequestBody ItemDto itemDto) {
        log.info("Updating item of user by item id: {}", itemId);
        return itemService.update(userId, itemId, itemDto);
    }
}
