package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundByRequestException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.storage.ItemStorageIM;

import javax.validation.constraints.Max;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemServiceImpl implements ItemService {
    private final ItemStorageIM itemStorage;
    @Autowired
    public ItemServiceImpl(ItemStorageIM itemStorage){
        this.itemStorage = itemStorage;
    }

    @Override
    public Item addItem(Integer userId, ItemDto itemDto){
        if (itemDto.getAvailable() == null || itemDto.getDescription() == null || itemDto.getName() == null
                                           || itemDto.getName().isBlank()) {
            throw new NotFoundByRequestException("More info needed");
        }
        return itemStorage.addItem(userId, itemDto);
    }

    @Override
    public List<Item> getItemOfUserId(Integer userId){
        return itemStorage.getItemOfUserId(userId);
    }

    @Override
    public Optional<Item> getItemById(Integer id){
        return itemStorage.getItemById(id);
    }

    @Override
    public List<Item> searchItem(String text){
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemStorage.searchItem(text);
    }

    @Override
    public Item update(Integer userId, Integer itemId, ItemDto itemDto){
        return itemStorage.update(userId, itemId, itemDto);
    }


}
