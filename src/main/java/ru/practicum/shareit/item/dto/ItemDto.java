package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class ItemDto {

    @Positive
    private Integer id;

    @NotBlank(message = "Ошибка! название вещи не может быть пустым.")
    private String name;
    @NotBlank(message = "Ошибка! Развёрнутое описание вещи не может быть пустым.")
    private String description;

    private Boolean available;

    private User owner;

}