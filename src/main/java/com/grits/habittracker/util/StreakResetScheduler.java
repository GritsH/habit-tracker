package com.grits.habittracker.util;

import com.grits.habittracker.dao.StreakDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class StreakResetScheduler {

    private final StreakDao streakDao;

    @Scheduled(cron = "0 * * * * *")
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            timeout = 30
    )
    public void resetCurrentStreak() {
        log.info("Streak current continuation reset begins");
        streakDao.resetAllMissedStreaks();
        log.info("Streak current continuation reset ends");
    }
}
