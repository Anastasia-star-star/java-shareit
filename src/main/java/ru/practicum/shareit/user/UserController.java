package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.service.UserServiceIM;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserServiceIM userService;

    @Autowired
    public UserController(UserServiceIM userService){
        this.userService = userService;
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}") UserDto updateUser(@PathVariable Integer id, @RequestBody UserDto userDto){
        return userService.updateUser(id, userDto);
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}") UserDto getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/{userId}") boolean deleteUserById(@PathVariable Integer id){
        return userService.deleteUser(id);
    }
}
