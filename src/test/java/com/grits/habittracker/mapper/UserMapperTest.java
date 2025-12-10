package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.model.request.SignupRequest;
import com.grits.habittracker.model.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    @DisplayName("should map all fields from dto")
    void dtoToEntity() {
        SignupRequest request = new SignupRequest(
                "test@example.com",
                "password123",
                "John",
                "Doe",
                "userName!@!!"
        );

        User user = userMapper.dtoToEntity(request);

        assertThat(user).isNotNull();
        assertThat(request.getEmail()).isEqualTo(user.getEmail());
        assertThat(request.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(request.getLastName()).isEqualTo(user.getLastName());
        assertThat(request.getUsername()).isEqualTo(user.getUsername());
        assertThat(user.getPassword()).isNotNull();
    }

    @Test
    @DisplayName("should be null if dto is null")
    void dtoToEntity_DtoNull() {
        assertThat(userMapper.dtoToEntity(null)).isNull();
    }

    @Test
    @DisplayName("should map all fields from entity")
    void entityToDto() {
        User user = new User();
        user.setId("id");
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("userName!@!!");

        UserResponse response = userMapper.entityToDto(user);

        assertThat(response).isNotNull();
        assertThat(response).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    @DisplayName("should be null if entity is null")
    void entityToDto_EntityNull() {
        assertThat(userMapper.entityToDto(null)).isNull();
    }
}