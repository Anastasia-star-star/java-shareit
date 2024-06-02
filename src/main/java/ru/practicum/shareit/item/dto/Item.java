package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    private Integer id;

    private String name;

    private String description;

    private boolean available;

    private User owner;

    private ItemRequest request;
}
