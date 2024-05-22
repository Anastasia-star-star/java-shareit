package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static ru.practicum.shareit.item.ItemMapper.itemDtoToItem;

@Repository
public class InMemoryItemStorage implements ItemStorage{
    public HashMap<Integer, ArrayList<Item>> itemsOfUser = new HashMap<>();
    int idOfItem = 1;

    private Integer generateId(){
        return idOfItem++;
    }
    public ItemDto addItem(int userId, ItemDto itemDto){
        Item item = itemDtoToItem(itemDto);
        if (itemsOfUser.containsKey(userId)){
            itemsOfUser.get(userId).add(ItemMapper.itemDtoToItem(itemDto));
        }
        return itemDto;
    }


}
