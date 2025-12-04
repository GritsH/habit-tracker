package com.grits.habittracker.model;

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

    public static UserDto fromEntity(){
        return UserDto.builder().build();
    }
}
