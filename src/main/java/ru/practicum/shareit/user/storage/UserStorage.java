package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    Optional<User> addUser(User user);

    List<User> getUsers();

    Optional<User> getUserById(Integer id);

    Optional<User> updateUser(Integer userId, User user);

    boolean deleteUserById(Integer userId);
}
