package team3.secondhand.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import team3.secondhand.entity.TokenEntity;
import team3.secondhand.entity.UserEntity;
import team3.secondhand.exception.UserNotExistException;
import team3.secondhand.util.JwtUtil;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    // use jwtUtil to help us generate token
    private final JwtUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public TokenEntity authenticate(UserEntity user) throws UserNotExistException {
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));
        } catch (AuthenticationException exception) {
            throw new UserNotExistException("User Doesn't Exist");
        }

        if (auth == null || !auth.isAuthenticated()) {
            throw new UserNotExistException("User has not permission to access");
        }

        return new TokenEntity(jwtUtil.generateToken(user.username()));
    }



}
