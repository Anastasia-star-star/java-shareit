package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserStorageIM implements UserStorage {
    public List<User> users = new ArrayList<>();
    private Integer id = 0;

    @Override
    public List<UserDto> getUsers(){
        return users.stream()
                .map(user -> UserMapper.UserToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> addUser(User user){
        if (users.stream()
                .filter(u -> u.getEmail().equals(user.getEmail()))
                .count() > 0){
            return Optional.empty();
        }
        user.setId(id++);
        users.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<UserDto> updateUser(Integer userId, User user) {
        if (!users.contains(userId)) {
            return Optional.empty();
        }
        if (validate(userId, user.getEmail()) != 1) {
            if (users.stream()
                    .filter(u -> u.getEmail().equals(user.getEmail()))
                    .count() > 0){
                return Optional.empty();
            }
        }

        User userForUpdate = users.get(userId);
        if (user.getEmail() != null && user.getName() != null) {
            userForUpdate.setEmail(user.getEmail());
            userForUpdate.setName(user.getName());
        }
        return Optional.of(UserMapper.UserToUserDto(userForUpdate));
    }

    @Override
    public Optional<UserDto> getUserById(Integer id){
        return users.stream()
                .filter(user1 -> user1.getId().equals(id))
                .map(user -> UserMapper.UserToUserDto(user))
                .findFirst();
    }
    @Override
    public boolean deleteUserById(Integer userId) {
        if (users.contains(userId)) {
            users.remove(userId);
            return true;
        }
        return false;
    }

    private long validate(Integer userId, String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .filter(u -> u.getId().equals(userId))
                .count();
    }
}
