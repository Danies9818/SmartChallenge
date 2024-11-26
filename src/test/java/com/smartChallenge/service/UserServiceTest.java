package com.smartChallenge.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.smartChallenge.dto.UserRequest;
import com.smartChallenge.dto.UserResponse;
import com.smartChallenge.exception.ValidationException;
import com.smartChallenge.jwt.JWTUtil;
import com.smartChallenge.model.Phone;
import com.smartChallenge.model.User;
import com.smartChallenge.repository.UserRepository;
import com.smartChallenge.util.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.UUID;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private Validation validation;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequest userRequest;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        generateSavedUser();
        generateUserRequest();

    }



    @Test
    void shouldRegisterUserSuccessfully() {

        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(jwtUtil.generateToken(userRequest.getEmail())).thenReturn("mocked-token");
        when(userRepository.save(any())).thenReturn(user);

        UserResponse userResponse = userService.saveUser(userRequest);


        assertNotNull(userResponse);
        assertEquals(userRequest.getName(), userResponse.getName());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.saveUser(userRequest);
        });

        assertEquals("El correo ya registrado", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsBad() {

        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        doThrow(new ValidationException("El formato de la contraseña es inválido:"))
                .when(validation)
                .validateUserRequest(any(), any());
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.saveUser(userRequest);
        });

        assertEquals(true, exception.getMessage().contains("El formato de la contraseña es inválido:"));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsBad() {

        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        doThrow(new ValidationException("El formato de la contraseña es inválido:"))
                .when(validation)
                .validateUserRequest(any(), any());
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.saveUser(userRequest);
        });

        assertEquals(true, exception.getMessage().contains("El formato de la contraseña es inválido:"));

        verify(userRepository, never()).save(any(User.class));
    }

    private void generateUserRequest() {
        userRequest = new UserRequest();
        userRequest.setName("Juan Rodriguez");
        userRequest.setEmail("juan@rodriguez.org");
        userRequest.setPassword("Hunter2@");

        UserRequest.PhoneRequest phoneRequest1 = new UserRequest.PhoneRequest();
        phoneRequest1.setNumber("1234567");
        phoneRequest1.setCitycode("1");
        phoneRequest1.setContrycode("57");

        UserRequest.PhoneRequest phoneRequest2 = new UserRequest.PhoneRequest();
        phoneRequest2.setNumber("7654321");
        phoneRequest2.setCitycode("2");
        phoneRequest2.setContrycode("57");

        userRequest.setPhones(Arrays.asList(phoneRequest1, phoneRequest2));
    }


    private void generateSavedUser() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Juan Rodriguez");
        user.setEmail("juan@rodriguez.org");
        user.setPassword("Hunter2@");

        Phone phone1 = new Phone();
        phone1.setNumber("1234567");
        phone1.setCitycode("1");
        phone1.setContrycode("57");

        Phone phone2 = new Phone();
        phone1.setNumber("1234567");
        phone1.setCitycode("2");
        phone1.setContrycode("57");

        user.setPhones(Arrays.asList(phone1, phone2));
    }
}

