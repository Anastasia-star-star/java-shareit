package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto add(@Valid @RequestBody @NotNull UserDto user) {
        log.info("Adding new user");
        return userService.add(UserMapper.toUser(user));
    }

    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    @PutMapping
    public UserDto updateUser(@Valid @RequestBody @NotNull UserDto user) {
        log.info("Updating new user");
        return userService.updateUser(UserMapper.toUser(user));
    }

    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    @PatchMapping("{id}")
    public UserDto patchUpdate(@PathVariable long id,
                               @RequestBody Map<String, String> updates) {
        log.info("Patching new user");
        return userService.patchUpdate(id, updates);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("{id}")
    public UserDto getUserDtoById(@PathVariable(required = false) long id) {
        log.info("Getting user");
        return userService.getUserDtoById(id);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Getting all users");
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.GONE)
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable long id) {
        log.info("Deleting user");
        userService.deleteUser(id);
    }
}
