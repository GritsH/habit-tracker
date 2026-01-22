package com.grits.server.mapper;

import com.grits.server.entity.User;
import com.grits.api.model.request.SignupRequest;
import com.grits.api.model.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(SignupRequest signupRequest);

    UserResponse entityToDto(User user);
}
