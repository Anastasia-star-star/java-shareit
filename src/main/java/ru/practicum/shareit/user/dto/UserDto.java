package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class UserDto {
    private Long id;

    @NotBlank()
    @Pattern(regexp = "\\S+")
    private String name;

    @Email()
    @NotBlank()
    private String email;
}