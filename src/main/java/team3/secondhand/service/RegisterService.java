package team3.secondhand.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team3.secondhand.entity.UserEntity;
import team3.secondhand.exception.UserAlreadyExistException;
import team3.secondhand.repository.UserRepository;

@Service
public class RegisterService {
    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void add(UserEntity user) throws UserAlreadyExistException {
        // if user has already exists, we throw exception
        if(userRepository.existsById(user.username())) {
            throw new UserAlreadyExistException("user already exists!");
        }
        userRepository.add(user.username(),user.password(),user.location(), user.enabled());
    }
}
