package com.smartChallenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartChallenge.ExceptionController.RestExceptionHandler;
import com.smartChallenge.dto.UserRequest;
import com.smartChallenge.dto.UserResponse;
import com.smartChallenge.exception.ValidationException;
import com.smartChallenge.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;


    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private UserRequest userRequest;


    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();

        generateSavedUser();
        generateUserRequest();
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        when(userService.saveUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk());

        verify(userService, times(1)).saveUser(any(UserRequest.class));
    }

    @Test
    void shouldReturnErrorWhenEmailAlreadyExists() throws Exception {
        doThrow(new ValidationException("El correo ya registrado")).when(userService).saveUser(any());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).saveUser(any(UserRequest.class));
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
        userResponse = new UserResponse();
        userResponse.setId(UUID.randomUUID());
        userResponse.setName("Juan Rodriguez");
        userResponse.setEmail("juan@rodriguez.org");
        userResponse.setToken("sssssss");

        UserResponse.PhoneResponse phone1 = new UserResponse.PhoneResponse();
        phone1.setNumber("1234567");
        phone1.setCitycode("1");
        phone1.setContrycode("57");

        UserResponse.PhoneResponse phone2 = new UserResponse.PhoneResponse();
        phone1.setNumber("1234567");
        phone1.setCitycode("2");
        phone1.setContrycode("57");

        userResponse.setPhones(Arrays.asList(phone1, phone2));
    }
}

