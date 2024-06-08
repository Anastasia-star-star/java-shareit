package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserDto add(User user);

    UserDto updateUser(User user);

    UserDto patchUpdate(long id, Map<String, String> updates);

    User getUserById(long id);

    UserDto getUserDtoById(long id);

    List<UserDto> getAllUsers();

    void deleteUser(long userId);

    void deleteAll();

}
