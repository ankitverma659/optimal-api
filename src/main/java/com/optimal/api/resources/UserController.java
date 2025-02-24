package com.optimal.api.resources;

import com.optimal.api.facades.UserFacade;
import com.optimal.api.models.bo.UserBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing user-related operations.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Operations related to user management")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserFacade userFacade;

    /**
     * Fetch paginated list of all users.
     *
     * @param pageable Pageable object for pagination settings
     * @return Page of UserBO
     */
    @Operation(summary = "Get all users", description = "Fetch paginated list of all users")
    @GetMapping("/")
    public ResponseEntity<Page<UserBO>> getAllUsers(Pageable pageable) {
        logger.info("Fetching all users with pagination settings: {}", pageable);
        Page<UserBO> users = userFacade.getAllUsers(pageable);
        logger.info("Retrieved {} users", users.getTotalElements());
        return ResponseEntity.ok(users);
    }

    /**
     * Fetch a user by their unique username.
     *
     * @param username Username of the user
     * @return UserBO object
     */
    @Operation(summary = "Get user by username", description = "Fetch a user by their unique username")
    @GetMapping("/{username}")
    public ResponseEntity<UserBO> getUser(
            @Parameter(description = "Username of the user", example = "john_doe")
            @PathVariable String username) {
        logger.info("Fetching user with username: {}", username);
        UserBO user = userFacade.getUserByUsername(username);
        logger.info("User found: {}", user);
        return ResponseEntity.ok(user);
    }

    /**
     * Create a new user.
     *
     * @param user UserBO object containing user details
     * @return Created UserBO object
     */
    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @PostMapping("/")
    public ResponseEntity<UserBO> createUser(@Valid @RequestBody UserBO user) {
        logger.info("Creating new user: {}", user);
        UserBO createdUser = userFacade.createUser(user);
        logger.info("User created successfully: {}", createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Update an existing user's details.
     *
     * @param updatedUser Updated UserBO object
     * @return Updated UserBO object
     */
    @Operation(summary = "Update user details", description = "Modify an existing user's information")
    @PutMapping("/")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserBO updatedUser) {
        logger.info("Updating user: {}", updatedUser);
        UserBO user = userFacade.updateUser(updatedUser);
        logger.info("User updated successfully: {}", user);
        return ResponseEntity.ok(user);
    }

    /**
     * Delete a user by their username.
     *
     * @param username Username of the user to be deleted
     */
    @Operation(summary = "Delete a user", description = "Remove a user by their username")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(
            @Parameter(description = "Username of the user to delete", example = "john_doe")
            @PathVariable String username) {
        logger.info("Deleting user with username: {}", username);
        userFacade.deleteUser(username);
        logger.info("User deleted successfully.");
        return ResponseEntity.ok("User deleted successfully.");
    }

    /**
     * Generate a specified number of random users.
     *
     * @param number Number of users to generate
     * @return List of randomly generated UserBO objects
     */
    @Operation(summary = "Generate random users", description = "Generate a specified number of random users")
    @GetMapping("/generate/{number}")
    public ResponseEntity<List<UserBO>> generateUsers(
            @Parameter(description = "Number of random users to generate", example = "5")
            @PathVariable Integer number) {
        logger.info("Generating {} random users", number);
        List<UserBO> users = userFacade.generateRandomUsers(number);
        logger.info("Generated {} users successfully", users.size());
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieve users organized in a hierarchical tree structure.
     *
     * @return User tree structure
     */
    @Operation(summary = "Get user tree structure", description = "Retrieve users organized in a hierarchical tree")
    @GetMapping("/tree/")
    public ResponseEntity<Map<String, Map<String, Map<String, List<UserBO>>>>> getUserTree() {
        logger.info("Fetching user tree structure");
        Map<String, Map<String, Map<String, List<UserBO>>>> userTree = userFacade.getUserTree();
        logger.info("User tree retrieved successfully");
        return ResponseEntity.ok(userTree);
    }
}
