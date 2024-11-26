package com.smartChallenge.dto;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String token;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private boolean isActive;
    private List<PhoneResponse> phones;

    @Data
    public static class PhoneResponse {
        private String number;
        private String citycode;
        private String contrycode;
    }
}
