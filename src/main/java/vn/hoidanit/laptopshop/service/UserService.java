package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "Hello from service";
    }

    public User handSaveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
