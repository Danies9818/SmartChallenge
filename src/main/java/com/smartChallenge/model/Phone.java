package com.smartChallenge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "phones")
@Data
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String number;

    private String citycode;

    private String contrycode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
