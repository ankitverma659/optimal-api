package com.optimal.api.resources;

import com.optimal.api.facades.UserFacade;
import com.optimal.api.models.bo.UserBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private UserController userController;

    private UserBO userBO;

    @BeforeEach
    void setUp() {
        userBO = new UserBO();
        userBO.setUsername("john_doe");
        userBO.setEmail("john.doe@example.com");
    }

    @Test
    void testGetAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserBO> mockPage = new PageImpl<>(List.of(userBO), pageable, 1);

        when(userFacade.getAllUsers(pageable)).thenReturn(mockPage);

        ResponseEntity<Page<UserBO>> response = userController.getAllUsers(pageable);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getTotalElements());
        verify(userFacade, times(1)).getAllUsers(pageable);
    }

    @Test
    void testGetUserByUsername_Success() {
        when(userFacade.getUserByUsername("john_doe")).thenReturn(userBO);

        ResponseEntity<UserBO> response = userController.getUser("john_doe");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john_doe", Objects.requireNonNull(response.getBody()).getUsername());
        verify(userFacade, times(1)).getUserByUsername("john_doe");
    }

    @Test
    void testCreateUser_Success() {
        when(userFacade.createUser(userBO)).thenReturn(userBO);

        ResponseEntity<UserBO> response = userController.createUser(userBO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("john_doe", Objects.requireNonNull(response.getBody()).getUsername());
        verify(userFacade, times(1)).createUser(userBO);
    }

    @Test
    void testUpdateUser_Success() {
        when(userFacade.updateUser(userBO)).thenReturn(userBO);

        ResponseEntity<?> response = userController.updateUser(userBO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john_doe", ((UserBO) response.getBody()).getUsername());
        verify(userFacade, times(1)).updateUser(userBO);
    }

    @Test
    void testDeleteUser_Success() {
        doNothing().when(userFacade).deleteUser("john_doe");

        ResponseEntity<String> response = userController.deleteUser("john_doe");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully.", response.getBody());
        verify(userFacade, times(1)).deleteUser("john_doe");
    }

    @Test
    void testGenerateUsers_Success() {
        List<UserBO> mockUsers = List.of(userBO, userBO);

        when(userFacade.generateRandomUsers(2)).thenReturn(mockUsers);

        ResponseEntity<List<UserBO>> response = userController.generateUsers(2);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(userFacade, times(1)).generateRandomUsers(2);
    }

    @Test
    void testGetUserTree_Success() {
        Map<String, Map<String, Map<String, List<UserBO>>>> mockTree = new HashMap<>();
        when(userFacade.getUserTree()).thenReturn(mockTree);

        ResponseEntity<Map<String, Map<String, Map<String, List<UserBO>>>>> response = userController.getUserTree();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userFacade, times(1)).getUserTree();
    }
}
