package puce.playwithemotions.service;

import org.springframework.stereotype.Service;
import puce.playwithemotions.entity.User;
import puce.playwithemotions.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNombre(userDetails.getNombre());
                    user.setEmail(userDetails.getEmail());
                    user.setRole(userDetails.getRole());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}

