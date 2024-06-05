package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.Item;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Item addItem(int userId, ItemDto itemDto);

    List<Item> getItemOfUserId(Integer userId);

    Optional<Item> getItemById(Integer id);

    List<Item> searchItem(String text);

    Item update(Integer userId, Integer itemId, ItemDto itemDto);
}
