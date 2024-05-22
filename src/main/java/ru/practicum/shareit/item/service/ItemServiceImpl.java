package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.storage.InMemoryItemStorage;

@Repository
public class ItemServiceImpl implements ItemService {
    private final InMemoryItemStorage itemStorage;
    @Autowired
    public ItemServiceImpl(InMemoryItemStorage itemStorage){
        this.itemStorage = itemStorage;
    }
    public ItemDto addItem(int userId, ItemDto itemDto){
        return itemStorage.addItem(userId, itemDto);
    }
}
