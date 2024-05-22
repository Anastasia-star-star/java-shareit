package ru.practicum.shareit.user.service;


import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);
    List<UserDto> getUsers();
    UserDto getUserById(Integer id);
    UserDto updateUser(Integer id, UserDto user);
    boolean deleteUser(Integer id);
}
