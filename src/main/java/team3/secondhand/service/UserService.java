package team3.secondhand.service;

import org.springframework.stereotype.Service;
import team3.secondhand.entity.UserEntity;
import team3.secondhand.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
