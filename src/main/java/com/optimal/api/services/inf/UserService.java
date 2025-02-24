package com.optimal.api.services.inf;

import com.optimal.api.models.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    /**
     * Retrieves all users with pagination support.
     *
     * @param pageable Pagination information
     * @return Page of UserDTO objects
     */
    Page<UserDTO> getAllUsers(Pageable pageable);

    /**
     * Saves a list of users to the database.
     *
     * @param userDTOS List of UserDTO objects to save
     * @return List of saved UserDTO objects
     */
    List<UserDTO> saveAllUsers(List<UserDTO> userDTOS);

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user
     * @return UserDTO object if found
     */
    UserDTO getUserByUsername(String username);

    /**
     * Creates a new user in the database.
     *
     * @param user UserDTO object containing user details
     * @return The saved UserDTO object
     */
    UserDTO createUser(UserDTO user);

    /**
     * Updates an existing user's information.
     *
     * @param updatedUser Updated UserDTO object
     * @return The updated UserDTO object
     */
    UserDTO updateUser(UserDTO updatedUser);

    /**
     * Deletes a user by username.
     *
     * @param username The username of the user to delete
     */
    void deleteUser(String username);
}
