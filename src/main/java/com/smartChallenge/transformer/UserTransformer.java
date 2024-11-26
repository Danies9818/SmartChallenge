package com.smartChallenge.transformer;

import com.smartChallenge.dto.UserResponse;
import com.smartChallenge.model.User;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class UserTransformer {
    public static UserResponse transformToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setToken(user.getToken());
        userResponse.setCreated(user.getCreated());
        userResponse.setModified(user.getModified());
        userResponse.setLastLogin(user.getLastLogin());
        userResponse.setActive(user.isActive());

        userResponse.setPhones(user.getPhones().stream().map(phone -> {
            UserResponse.PhoneResponse phoneResponse = new UserResponse.PhoneResponse();
            phoneResponse.setNumber(phone.getNumber());
            phoneResponse.setCitycode(phone.getCitycode());
            phoneResponse.setContrycode(phone.getContrycode());
            return phoneResponse;
        }).collect(Collectors.toList()));



        return userResponse;
    }
}
