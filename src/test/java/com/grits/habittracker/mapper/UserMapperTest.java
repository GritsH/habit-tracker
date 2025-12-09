package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.model.request.SignupRequest;
import com.grits.habittracker.model.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        assertNotNull(user);
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getUsername(), user.getUsername());
        assertNotNull(user.getPassword());
    }

    @Test
    @DisplayName("should be null if dto is null")
    void dtoToEntity_DtoNull() {
        assertNull(userMapper.dtoToEntity(null));
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

        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getLastName(), response.getLastName());
        assertEquals(user.getUsername(), response.getUsername());
    }

    @Test
    @DisplayName("should be null if entity is null")
    void entityToDto_EntityNull() {
        assertNull(userMapper.entityToDto(null));
    }
}