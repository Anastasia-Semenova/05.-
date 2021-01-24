package ru.itis.javalab.models;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String login;
    private String password;
    private String uuid;
}
