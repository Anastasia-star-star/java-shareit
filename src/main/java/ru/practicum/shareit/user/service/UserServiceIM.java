package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Repository
public class UserServiceIM implements UserService{
    private final UserStorage userStorage;

    @Autowired
    public UserServiceIM(UserStorage userStorage){
        this.userStorage = userStorage;
    }

    @Override
    public UserDto addUser(UserDto userDto){
        return UserMapper.UserToUserDto(userStorage.addUser(UserMapper.userDtoToUser(userDto))
                .orElseThrow(() -> new ValidationException("Ошибка создания пользователя")));
    }
    @Override
    public List<UserDto> getUsers(){
        return userStorage.getUsers();
    }

    @Override
    public UserDto getUserById(Integer id){
        return userStorage.getUserById(id).orElseThrow(() ->
                new ValidationException("Не найде пользователь с id = " + id));
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto){
        return userStorage.updateUser(id, UserMapper.userDtoToUser(userDto))
                .orElseThrow(() -> new ValidationException("Ошибка обновления пользователя"));
    }

    @Override
    public boolean deleteUser(Integer id){
        return userStorage.deleteUserById(id);
    }
}
