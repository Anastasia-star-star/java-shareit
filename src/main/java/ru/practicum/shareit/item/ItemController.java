package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ItemController {
    private final ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @PostMapping
    public Item addItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                           @Valid @RequestBody ItemDto itemDto) {
        return itemService.addItem(userId, itemDto);
    }

    @GetMapping
    public List<Item> getItemOfUserId(@RequestHeader("X-Sharer-User-Id") Integer userId){
        return itemService.getItemOfUserId(userId);
    }

    @GetMapping("/{itemId}")
    public Optional<Item> getItemById(@PathVariable Integer itemId){
        return itemService.getItemById(itemId);
    }

    @GetMapping("/search")
    public List<Item> searchItem(@RequestParam String text){
        return itemService.searchItem(text);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestHeader("X-Sharer-User-Id") Integer userId,
                       @PathVariable Integer itemId,
                       @Valid @RequestBody ItemDto itemDto){
        return itemService.update(userId, itemId, itemDto);
    }
}
