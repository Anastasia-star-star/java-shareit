package ru.practicum.shareit.user.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank(message = "Ошибка! e-mail не может быть пустым.")
    @Email(message = "Ошибка! Неверный e-mail.")
    private String email;

}
