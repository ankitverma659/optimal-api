package com.optimal.api.facades;

import com.optimal.api.models.bo.UserBO;
import com.optimal.api.models.dtos.UserDTO;
import com.optimal.api.services.inf.UserService;
import com.optimal.api.services.external.inf.RandomUserGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private RandomUserGenerator randomUserGenerator;

    public Page<UserBO> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable).map(UserDTO::toBO);
    }

    public UserBO getUserByUsername(String username) {
        return userService.getUserByUsername(username).toBO();
    }

    public UserBO createUser(UserBO user) {
        return userService.createUser(user.toRO()).toBO();
    }

    public UserBO updateUser(UserBO updatedUser) {
        return userService.updateUser(updatedUser.toRO()).toBO();
    }

    public void deleteUser(String username) {
        userService.deleteUser(username);
    }

    public List<UserBO> generateRandomUsers(int number) {
        List<UserDTO> randomUsers = randomUserGenerator.getRandomUsers(number);
        List<UserDTO> userDTOS = userService.saveAllUsers(randomUsers);
        return userDTOS.stream().map(UserDTO::toBO).collect(Collectors.toList());
    }

    public Map<String, Map<String, Map<String, List<UserBO>>>> getUserTree() {
        List<UserDTO> users = userService.getAllUsers(PageRequest.of(0, Integer.MAX_VALUE))
                .getContent();
        return users.stream().map(UserDTO::toBO)
                .collect(Collectors.groupingBy(
                        UserBO::getCountry,
                        Collectors.groupingBy(
                                UserBO::getState,
                                Collectors.groupingBy(
                                        UserBO::getCity,
                                        Collectors.toList() // Store the full User objects
                                )
                        )
                ));
    }
}
