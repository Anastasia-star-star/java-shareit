package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotEmpty;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class ItemDto {

    private Integer id;

    @NotEmpty(message = "Ошибка! название вещи не может быть пустым.")
    private String name;
    @NotEmpty(message = "Ошибка! Развёрнутое описание вещи не может быть пустым.")
    private String description;

    private Boolean available;

    private User owner;

}