package team3.secondhand.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team3.secondhand.entity.UserEntity;
import team3.secondhand.entity.UserRole;
import team3.secondhand.service.RegisterService;

@RestController
public class RegisterController {
    private final RegisterService registerService;

    private final PasswordEncoder passwordEncoder;

    public RegisterController(RegisterService registerService, PasswordEncoder passwordEncoder) {
        this.registerService = registerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public void signUp(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("location") String location
            ) {
        // for application safety, we need use spring security PasswordEncoder class to encode user's password
        UserEntity userEntity = new UserEntity(username, passwordEncoder.encode(password),location, true);
        final UserRole role = UserRole.ROLE_USER;
        registerService.add(userEntity, role);
    }

}
