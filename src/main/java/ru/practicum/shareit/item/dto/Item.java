package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {

    @Positive
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private boolean available;

    private User owner;

    private ItemRequest request;
}
