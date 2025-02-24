package com.optimal.api.exceptionResource;

import com.optimal.api.customExceptions.DuplicateResourceException;
import com.optimal.api.customExceptions.RandomUserServiceException;
import com.optimal.api.customExceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to manage and standardize API error responses.
 * This class handles various exceptions that may occur in the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors for @Valid annotated request bodies.
     * Extracts and returns field-specific error messages.
     *
     * @param ex MethodArgumentNotValidException
     * @return Map of field names and error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("Bad request: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    /**
     * Handles duplicate resource exceptions, typically thrown when trying to create
     * an entity that already exists.
     *
     * @param ex DuplicateResourceException
     * @return Map containing the error message
     */
    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
    public Map<String, String> handleDuplicateResourceException(DuplicateResourceException ex) {
        logger.error("Duplicate resource: {}", ex.getMessage(), ex);
        return Map.of("error", ex.getMessage());
    }

    /**
     * Handles user not found exceptions, thrown when a requested user is not found.
     *
     * @param ex UserNotFoundException
     * @return Map containing the error message
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404 Not Found
    public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("User not found: {}", ex.getMessage(), ex);
        return Map.of("error", ex.getMessage());
    }

    /**
     * Handles invalid type mismatches in URL path variables or request parameters.
     * Example: When a string is passed instead of an integer.
     *
     * @param ex MethodArgumentTypeMismatchException
     * @return ResponseEntity with a detailed error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        logger.error("Invalid input type: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Invalid input.", "details", ex.getMessage())
        );
    }

    /**
     * Handles database access exceptions, including SQL errors and transaction failures.
     *
     * @param ex DataAccessException
     * @return ResponseEntity with a 500 Internal Server Error status
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseExceptions(DataAccessException ex) {
        logger.error("Database error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Database error: " + ex.getMessage());
    }

    /**
     * Handles random user service exceptions, when the call to the api fails.
     *
     * @param ex RandomUserServiceException
     * @return ResponseEntity with a 500 Internal Server Error status
     */
    @ExceptionHandler(RandomUserServiceException.class)
    public ResponseEntity<String> handleRandomUserServiceException(RandomUserServiceException ex) {
        logger.error("Random user service failed: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Random user service failed: " + ex.getMessage());
    }

    /**
     * Handles entity not found exceptions, commonly used with JPA when an entity
     * is not found in the database.
     *
     * @param ex EntityNotFoundException
     * @return ResponseEntity with a 404 Not Found status
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(EntityNotFoundException ex) {
        logger.info("Entity not found: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Entity not found: " + ex.getMessage());
    }

    /**
     * Handles all unexpected exceptions, ensuring the API does not expose internal errors.
     *
     * @param ex Generic Exception
     * @return ResponseEntity with a 500 Internal Server Error status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again.");
    }
}
