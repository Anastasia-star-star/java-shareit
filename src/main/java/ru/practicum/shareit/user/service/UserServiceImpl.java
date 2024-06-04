package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.RequestWithoutValueException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserStorage userStorage;

    @Override
    public User addUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new RequestWithoutValueException("Email is empty");
        }
        if (userDto.getName() == null || userDto.getName().isBlank()) {
            throw new RequestWithoutValueException("Name is empty");
        }
        return userStorage.addUser(UserMapper.userDtoToUser(userDto))
                .orElseThrow(() -> new ValidationException("Ошибка создания пользователя"));
    }

    @Override
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public User getUserById(Integer id) {
        log.info("Service getUserById with ID {}", id);
        if (id == null || id <= 0) {
            throw new ValidationException("ID is null or negative");
        }
        return userStorage.getUserById(id).orElseThrow(() ->
                new NotFoundException("Не найден пользователь с id = " + id));
    }

    @Override
    public User updateUser(Integer id, UserDto userDto) {
        return userStorage.updateUser(id, UserMapper.userDtoToUser(userDto))
                .orElseThrow(() -> new ValidationException("Ошибка обновления пользователя"));
    }

    @Override
    public boolean deleteUserById(Integer id) {
        return userStorage.deleteUserById(id);
    }
}
