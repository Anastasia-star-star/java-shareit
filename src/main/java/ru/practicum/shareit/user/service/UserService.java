package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

public interface UserService {
    User addUser(UserDto userDto);

    List<User> getUsers();

    User getUserById(Integer id);

    User updateUser(Integer id, UserDto user);

    boolean deleteUserById(Integer id);
}
