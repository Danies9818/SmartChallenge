package com.smartChallenge.service;

import com.smartChallenge.dto.UserRequest;
import com.smartChallenge.dto.UserResponse;
import com.smartChallenge.exception.ValidationException;
import com.smartChallenge.jwt.JWTUtil;
import com.smartChallenge.model.Phone;
import com.smartChallenge.model.User;
import com.smartChallenge.repository.UserRepository;
import com.smartChallenge.transformer.UserTransformer;
import com.smartChallenge.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private Validation validation;

    public UserResponse saveUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ValidationException("El correo ya registrado");
        }

        validation.validateUserRequest(userRequest.getEmail(), userRequest.getPassword());


        String token = jwtUtil.generateToken(userRequest.getEmail());

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setToken(token);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        user.setPhones(userRequest.getPhones().stream().map(phoneRequest -> {
            Phone phone = new Phone();
            phone.setNumber(phoneRequest.getNumber());
            phone.setCitycode(phoneRequest.getCitycode());
            phone.setContrycode(phoneRequest.getContrycode());
            phone.setUser(user);
            return phone;
        }).collect(Collectors.toList()));

        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now()); // Nuevo usuario, coincide con la creación
        user.setActive(true);

        return UserTransformer.transformToResponse(userRepository.save(user));
    }

}
