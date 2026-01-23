package com.grits.server.service;

import com.grits.api.model.response.StreakResponse;
import com.grits.server.dao.StreakDao;
import com.grits.server.mapper.StreakMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class StreakService {

    private final StreakDao streakDao;

    private final StreakMapper streakMapper;

    @Transactional(readOnly = true)
    public StreakResponse getStreak(String habitId, String userId) {
        log.info("Retrieving a streak for a habit {}", habitId);
        return streakMapper.toDto(streakDao.getStreak(habitId, userId));
    }
}
