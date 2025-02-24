package com.optimal.api.services.external.inf;

import com.optimal.api.models.dtos.UserDTO;
import java.util.List;

/**
 * Interface for generating random user data from an external source.
 * Implementations of this interface should handle fetching random users
 * from an API or generating mock data.
 */
public interface RandomUserGenerator {

    /**
     * Fetches a specified number of random users.
     *
     * @param number The number of random users to generate or fetch.
     * @return A list of {@link UserDTO} objects representing the generated users.
     * @throws IllegalArgumentException if the number is invalid (e.g., negative or zero).
     */
    List<UserDTO> getRandomUsers(int number);
}
