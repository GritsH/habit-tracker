package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.model.request.SignupRequest;
import com.grits.habittracker.model.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(SignupRequest signupRequest);

    UserResponse entityToDto(User user);
}
