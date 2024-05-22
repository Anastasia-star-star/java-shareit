package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class ItemDto {
    private int id;

    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    private Boolean available;

    private Integer request;

}