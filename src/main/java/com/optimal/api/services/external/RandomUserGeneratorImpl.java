package com.optimal.api.services.external;

import com.optimal.api.customExceptions.RandomUserServiceException;
import com.optimal.api.models.dtos.UserDTO;
import com.optimal.api.services.external.inf.RandomUserGenerator;
import com.optimal.api.services.external.model.RandomUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RandomUserGeneratorImpl implements RandomUserGenerator {

    private static final int TIME_OUT_IN_SECONDS = 5;
    private static final Logger logger = LoggerFactory.getLogger(RandomUserGeneratorImpl.class);
    private final WebClient webClient = WebClient.create("https://randomuser.me/api/");

    @Override
    public List<UserDTO> getRandomUsers(int number) {
        logger.info("Requesting {} random users from external API", number);

        try {
            RandomUserResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder.queryParam("results", number).build())
                    .retrieve()
                    .bodyToMono(RandomUserResponse.class)
                    .timeout(Duration.ofSeconds(TIME_OUT_IN_SECONDS))
                    .doOnSuccess(res -> logger.info("Successfully fetched {} users from API", res.getResults().size()))
                    .doOnError(e -> logger.error("Error fetching random users: {}", e.getMessage()))
                    .onErrorMap(Exception.class, ex -> new RandomUserServiceException("Failed to fetch random users", ex))
                    .block();

            if (response == null || response.getResults() == null) {
                logger.warn("Received empty response from API");
                throw new RandomUserServiceException("Empty response from Random User API");
            }

            List<UserDTO> users = response.getResults().stream().map(result -> {
                UserDTO user = UserDTO.builder()
                        .username(result.getLogin().getUsername())
                        .name(result.getName().getFirst() + " " + result.getName().getLast())
                        .email(result.getEmail())
                        .gender(result.getGender())
                        .picture(result.getPicture().getLarge())
                        .state(result.getLocation().getState())
                        .city(result.getLocation().getCity())
                        .country(result.getLocation().getCountry())
                        .build();
                logger.debug("Generated UserDTO: {}", user);
                return user;
            }).collect(Collectors.toList());

            logger.info("Successfully converted API response into {} UserDTO objects", users.size());
            return users;
        } catch (RandomUserServiceException e) {
            logger.error("Random user generation failed: {}", e.getMessage());
            throw e;
        }
    }
}
