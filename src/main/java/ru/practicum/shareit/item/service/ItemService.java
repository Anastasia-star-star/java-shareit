package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoOut;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOut;

import java.util.List;

public interface ItemService {

    ItemDtoOut add(Long userId, ItemDto itemDto);

    List<ItemDtoOut> getAll(Long userId, Integer from, Integer size);

    List<ItemDtoOut> search(Long userId, String text, Integer from, Integer size);

    CommentDtoOut createComment(Long userId, CommentDto commentDto, Long itemId);

    ItemDtoOut update(Long userId, Long itemId, ItemDto itemDto);

    ItemDtoOut getItemById(Long userId, Long itemId);

}