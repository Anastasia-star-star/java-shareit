package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody UserDto userDto) {
        log.info("Adding new user: {} ", userDto);
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    User updateUser(@PathVariable(value = "userId") Integer id,
                    @RequestBody UserDto userDto) {
        log.info("Updating user: {} ", userDto);
        return userService.updateUser(id, userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        log.info("Getting users");
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    User getUserById(@PathVariable(value = "userId") Integer id) {
        log.info("GET getUserById with PV {}", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    boolean deleteUserById(@PathVariable(value = "userId") Integer userId) {
        log.info("Delete user by Id: {} ", userId);
        return userService.deleteUserById(userId);
    }
}
