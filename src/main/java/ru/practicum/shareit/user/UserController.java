package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    User updateUser(@PathVariable(value = "userId") Integer id,
                    @Valid @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUserById(@PathVariable(value = "userId") Integer id) {
        log.info("GET getUserById with PV {}", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{userId}")
    boolean deleteUserById(@PathVariable(value = "userId") Integer id) {
        return userService.deleteUser(id);
    }
}
