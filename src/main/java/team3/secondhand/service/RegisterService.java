package team3.secondhand.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team3.secondhand.entity.AuthorityEntity;
import team3.secondhand.entity.UserEntity;
import team3.secondhand.entity.UserRole;
import team3.secondhand.exception.UserAlreadyExistException;
import team3.secondhand.repository.AuthorityRepository;
import team3.secondhand.repository.UserRepository;

@Service
public class RegisterService {
    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    public RegisterService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    public void add(UserEntity user, UserRole role) throws UserAlreadyExistException {
        // if user has already exists, we throw exception
        if(userRepository.existsById(user.username())) {
            throw new UserAlreadyExistException("user already exists!");
        }
        userRepository.add(user.username(),user.password(),user.location(), user.enabled());
        authorityRepository.add(user.username(), role.name());
    }
}
