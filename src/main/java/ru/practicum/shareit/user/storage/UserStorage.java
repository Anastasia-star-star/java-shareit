package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    Optional<User> addUser(User user);
    List<UserDto> getUsers();
    Optional<UserDto> getUserById(Integer id);
    Optional<UserDto> updateUser(Integer userId, User user);
    boolean deleteUserById(Integer userId);
}
