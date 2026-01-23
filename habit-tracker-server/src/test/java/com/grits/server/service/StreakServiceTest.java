package com.grits.server.service;

import com.grits.server.dao.StreakDao;
import com.grits.server.entity.Streak;
import com.grits.server.mapper.StreakMapper;
import com.grits.api.model.response.StreakResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StreakServiceTest {

    @Mock
    private StreakDao streakDao;

    @Mock
    private StreakMapper streakMapper;

    @InjectMocks
    private StreakService streakService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                streakDao,
                streakMapper
        );
    }

    @Test
    @DisplayName("should get a streak for a habit")
    void getStreak() {
        Streak streak = mock(Streak.class);
        StreakResponse streakResponse = mock(StreakResponse.class);

        when(streakDao.getStreak("habit_id", "user_id")).thenReturn(streak);
        when(streakMapper.toDto(streak)).thenReturn(streakResponse);

        StreakResponse response = streakService.getStreak("habit_id", "user_id");

        assertThat(response).isSameAs(streakResponse);
    }
}