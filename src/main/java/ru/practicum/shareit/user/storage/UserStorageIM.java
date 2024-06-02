package ru.practicum.shareit.user.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class UserStorageIM implements UserStorage {
    public List<User> users = new ArrayList<>();
    private Integer id = 1;

    @Override
    public List<User> getUsers(){
        return users;
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
    public Optional<User> updateUser(Integer userId, User user) {
        for (User checkUser : users) {
            if (checkUser.getId().equals(userId)) {
                log.info("OK. Id exists.");
                if (users.stream().noneMatch(u -> u.getEmail().equals(user.getEmail()))
                        || checkUser.getEmail().equals(user.getEmail())) {
                    log.info("OK. Such Email does not exists by others IDs.");
                    if (user.getName() != null && user.getEmail() != null) {
                        checkUser.setEmail(user.getEmail());
                        checkUser.setName(user.getName());
                        checkUser.setId(userId);
                        log.info(String.valueOf(user));
                        return Optional.of(checkUser);
                    } else if (user.getName() != null && user.getEmail() == null) {
                        checkUser.setName(user.getName());
                        checkUser.setId(userId);
                        log.info(String.valueOf(user));
                        return Optional.of(checkUser);
                    } else if (user.getName() == null && user.getEmail() != null) {
                        checkUser.setEmail(user.getEmail());
                        checkUser.setId(userId);
                        log.info(String.valueOf(user));
                        return Optional.of(checkUser);
                    } else {
                        log.info("No new data for user added");
                        return Optional.empty();
                    }
                }
                log.info(String.valueOf(user));
                return Optional.empty();
            }
        }
        log.info(String.valueOf(user));
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(Integer id){
        log.info("Storage getUserById with ID {}", id);
        return users.stream()
                .filter(user1 -> user1.getId().equals(id))
                .findFirst();
    }
    @Override
    public boolean deleteUserById(Integer userId) {
        for (User checkUser : users) {
            if (checkUser.getId().equals(userId)) {
                users.remove(checkUser);
                return true;
            }
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
