package com.smartChallenge.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private List<PhoneRequest> phones;

    @Data
    public static class PhoneRequest {
        private String number;
        private String citycode;
        private String contrycode;
    }
}
