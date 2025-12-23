package com.grits.habittracker.service;

import com.grits.habittracker.dao.StreakDao;
import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.mapper.StreakMapper;
import com.grits.habittracker.model.response.StreakResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StreakService {

    private final StreakDao streakDao;

    private final StreakMapper streakMapper;

    public void createNewStreak(String habitId) {
        log.info("Saving a new streak for a habit {}", habitId);
        streakDao.save(habitId);
        log.info("Streak for a habit {} saved successfully", habitId);
    }

    public void updateStreak(String habitId) {
        log.info("Updating streak for a habit {}", habitId);
        streakDao.updateStreak(habitId);
        log.info("Streak for a habit {} updated successfully", habitId);
    }

    public StreakResponse getStreak(String habitId, String userId) {
        log.info("Retrieving a streak for a habit {}", habitId);
        return streakMapper.toDto(streakDao.getStreak(habitId, userId));
    }

    @Scheduled(cron = "0 0 3 * * *")
    private void resetCurrentStreak() {
        log.info("Current streaks reset begins (check if was skipped)");

        streakDao.resetStreaks();

        log.info("Current streaks reset ends");
    }
}
