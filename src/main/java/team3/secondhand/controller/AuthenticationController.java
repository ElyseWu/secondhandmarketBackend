package team3.secondhand.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team3.secondhand.entity.TokenEntity;
import team3.secondhand.entity.UserEntity;
import team3.secondhand.service.AuthenticationService;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public TokenEntity authenticate(
            @RequestParam("username") String userName,
            @RequestParam("password") String password)
    {
        UserEntity userEntity = new UserEntity(userName, password, null, true);
        return authenticationService.authenticate(userEntity);
    }

}
