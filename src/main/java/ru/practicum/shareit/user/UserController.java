package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.markers.Create;
import ru.practicum.shareit.user.markers.Update;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto add(@Validated({Create.class}) @RequestBody UserDto userDto) {
        log.info("Запрос на добавление пользователя {}", userDto);
        return userService.add(userDto);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(@PathVariable Long userId) {
        log.info("Запрос на получение пользователя id = {}", userId);
        return userService.getById(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll() {
        log.info("Запрос на получение списка всех пользователей");
        return userService.getAll();
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@PathVariable Long userId,
                          @Validated({Update.class})
                          @RequestBody UserDto userDto) {
        log.info("Запрос на обновление пользователя id = {}", userId);
        return userService.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long userId) {
        log.info("Запрос на удаление пользователя id = {}", userId);
        userService.delete(userId);
    }
}