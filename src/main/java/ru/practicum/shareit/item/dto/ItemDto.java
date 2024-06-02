package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class ItemDto {

    private Integer id;

    private String name;

    private String description;

    private Boolean available;

    private User owner;

}