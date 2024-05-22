package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemDto;

public interface ItemStorage {
    ItemDto addItem(int userId, ItemDto itemDto);
}
