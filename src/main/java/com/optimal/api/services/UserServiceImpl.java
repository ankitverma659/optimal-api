package com.optimal.api.services;

import com.optimal.api.customExceptions.DuplicateResourceException;
import com.optimal.api.customExceptions.UserNotFoundException;
import com.optimal.api.models.dtos.UserDTO;
import com.optimal.api.respositories.UserRepository;
import com.optimal.api.services.inf.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        logger.info("Fetching all users with pagination: {}", pageable);
        return userRepository.findAll(pageable);
    }

    public List<UserDTO> saveAllUsers(List<UserDTO> userDTOS) {
        logger.info("Saving {} users to the database", userDTOS.size());
        return userRepository.saveAll(userDTOS);
    }

    public UserDTO getUserByUsername(String username) {
        logger.info("Fetching user with username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", username);
                    return new UserNotFoundException("User not found: " + username);
                });
    }

    public UserDTO createUser(UserDTO user) {
        logger.info("Creating new user: {}", user.getUsername());
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.warn("Username already exists: {}", user.getUsername());
            throw new DuplicateResourceException("Username already exists: " + user.getUsername());
        }
        return userRepository.save(user);
    }

    public UserDTO updateUser(UserDTO updatedUser) {
        logger.info("Updating user: {}", updatedUser.getUsername());
        if (!userRepository.existsByUsername(updatedUser.getUsername())) {
            logger.warn("User not found: {}", updatedUser.getUsername());
            throw new UserNotFoundException("User not found: " + updatedUser.getUsername());
        }
        return userRepository.save(updatedUser);
    }

    public void deleteUser(String username) {
        logger.info("Deleting user: {}", username);
        Optional<UserDTO> userDTO = userRepository.findByUsername(username);
        userDTO.ifPresentOrElse(user -> {
            userRepository.delete(user);
            logger.info("User {} deleted successfully", username);
        }, () -> {
            logger.warn("User not found: {}", username);
            throw new UserNotFoundException("User not found: " + username);
        });
    }
}
