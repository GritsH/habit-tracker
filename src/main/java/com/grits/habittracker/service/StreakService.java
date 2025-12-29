package com.grits.habittracker.service;

import com.grits.habittracker.dao.StreakDao;
import com.grits.habittracker.mapper.StreakMapper;
import com.grits.habittracker.model.response.StreakResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreakService {

    private final StreakDao streakDao;

    private final StreakMapper streakMapper;

    public StreakResponse getStreak(String habitId, String userId) {
        log.info("Retrieving a streak for a habit {}", habitId);
        return streakMapper.toDto(streakDao.getStreak(habitId, userId));
    }
}
