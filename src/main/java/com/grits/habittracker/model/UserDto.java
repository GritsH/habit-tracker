package com.grits.habittracker.model;

import com.grits.habittracker.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String username;

    public static UserDto fromEntity(User entity) {
        return UserDto.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .username(entity.getUsername())
                .build();
    }
}
