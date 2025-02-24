package com.optimal.api.services;

import com.optimal.api.customExceptions.DuplicateResourceException;
import com.optimal.api.customExceptions.UserNotFoundException;
import com.optimal.api.models.dtos.UserDTO;
import com.optimal.api.respositories.UserRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUsername("john_doe");
        userDTO.setEmail("john.doe@example.com");
    }

    @Test
    void testGetAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserDTO> mockPage = new PageImpl<>(List.of(userDTO), pageable, 1);

        when(userRepository.findAll(pageable)).thenReturn(mockPage);

        Page<UserDTO> result = userService.getAllUsers(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void testSaveAllUsers() {
        List<UserDTO> userList = List.of(userDTO);

        when(userRepository.saveAll(userList)).thenReturn(userList);

        List<UserDTO> result = userService.saveAllUsers(userList);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).saveAll(userList);
    }

    @Test
    void testGetUserByUsername_Success() {
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(userDTO));

        UserDTO result = userService.getUserByUsername("john_doe");

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(userRepository, times(1)).findByUsername("john_doe");
    }

    @Test
    void testGetUserByUsername_NotFound() {
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("john_doe"));
        verify(userRepository, times(1)).findByUsername("john_doe");
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.existsByUsername("john_doe")).thenReturn(false);
        when(userRepository.save(userDTO)).thenReturn(userDTO);

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(userRepository, times(1)).existsByUsername("john_doe");
        verify(userRepository, times(1)).save(userDTO);
    }

    @Test
    void testCreateUser_Duplicate() {
        when(userRepository.existsByUsername("john_doe")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(userDTO));
        verify(userRepository, times(1)).existsByUsername("john_doe");
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.existsByUsername("john_doe")).thenReturn(true);
        when(userRepository.save(userDTO)).thenReturn(userDTO);

        UserDTO result = userService.updateUser(userDTO);

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(userRepository, times(1)).existsByUsername("john_doe");
        verify(userRepository, times(1)).save(userDTO);
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.existsByUsername("john_doe")).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDTO));
        verify(userRepository, times(1)).existsByUsername("john_doe");
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(userDTO));

        userService.deleteUser("john_doe");

        verify(userRepository, times(1)).delete(userDTO);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("john_doe"));
        verify(userRepository, times(1)).findByUsername("john_doe");
    }
}
