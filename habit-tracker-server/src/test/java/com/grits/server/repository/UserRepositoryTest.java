package com.grits.server.repository;

import com.grits.server.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("username");
        testUser.setPassword("password");
        testUser.setEmail("email@gmail.com");
        testUser.setFirstName("firstName");
        testUser.setLastName("lastName");

        userRepository.save(testUser);
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(testUser);
    }

    @Test
    @DisplayName("should find user by email")
    void findByEmail() {
        User savedUser = userRepository.findByEmail(testUser.getEmail()).get();

        assertThat(savedUser).isSameAs(testUser);
    }

    @Test
    @DisplayName("should not find by the wrong email")
    void findByWrongEmail() {
        Optional<User> user = userRepository.findByEmail("wrongemail@gmail.com");

        assertThat(user).isNotPresent();
    }
}