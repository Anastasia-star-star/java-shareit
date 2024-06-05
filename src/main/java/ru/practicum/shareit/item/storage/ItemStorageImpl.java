package ru.practicum.shareit.item.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemStorageImpl implements ItemStorage {

    @Autowired
    private final UserService userService;

    private final HashMap<Integer, ArrayList<Item>> itemsOfUser = new HashMap<>();
    private int idOfItem = 1;

    private Integer generateId() {
        return idOfItem++;
    }

    @Override
    public Item addItem(int userId, ItemDto itemDto) {
        User owner = userService.getUserById(userId);

        itemDto.setOwner(owner);
        itemDto.setId(generateId());
        Item item = ItemMapper.itemDtoToItem(itemDto);
        if (itemsOfUser.containsKey(userId)) {
            itemsOfUser.get(userId).add(item);
        } else {
            ArrayList<Item> lst = new ArrayList<>();
            lst.add(item);
            itemsOfUser.put(userId, lst);
        }
        return item;
    }

    @Override
    public List<Item> getItemOfUserId(Integer userId) {
        return itemsOfUser.get(userId);
    }

    @Override
    public Optional<Item> getItemById(Integer id) {
        return itemsOfUser
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Item> searchItem(String text) {
        return itemsOfUser
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(item -> item.isAvailable())
                .collect(Collectors.toList());
    }

    @Override
    public Item update(Integer userId, Integer itemId, ItemDto itemDto) {
        if (itemsOfUser.get(userId) == null) {
            throw new NotFoundException("No such user found");
        }

        Item itemN = itemsOfUser.get(userId)
                .stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Id of item not found"));
        if (itemDto.getName() != null && !itemDto.getName().isBlank()) {
            itemN.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) {
            itemN.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            itemN.setAvailable(itemDto.getAvailable());
        }
        return itemN;
    }
}
