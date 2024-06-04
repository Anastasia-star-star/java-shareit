package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User addUser(@Valid @RequestBody UserDto userDto) {
        log.info("Adding new user: {} ", userDto);
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    User updateUser(@PathVariable(value = "userId") Integer id,
                    @RequestBody UserDto userDto) {
        log.info("Updating user: {} ", userDto);
        return userService.updateUser(id, userDto);
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Getting users");
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUserById(@PathVariable(value = "userId") Integer id) {
        log.info("GET getUserById with PV {}", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{userId}")
    boolean deleteUserById(@PathVariable(value = "userId") Integer userId) {
        log.info("Delete user by Id: {} ", userId);
        return userService.deleteUser(userId);
    }
}
