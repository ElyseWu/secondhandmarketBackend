package team3.secondhand.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team3.secondhand.entity.UserEntity;
import team3.secondhand.service.UserService;
import java.security.Principal;

@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public UserEntity getUser(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @GetMapping("/username")
    public UserEntity getCurrentUserName(Principal principal) {
        return userService.getUser(principal.getName());
    }
}
