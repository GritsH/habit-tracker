package com.grits.habittracker.repository;

import com.grits.habittracker.entity.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreakRepository extends JpaRepository<Streak, String> {

    Optional<Streak> findByHabitId(String id);

    void deleteAllByHabitId(String habitId);

    @Modifying
    @Query(value = """
            UPDATE habittracker.streak s 
            SET s.current_streak_days = 0, 
                s.last_updated = CURRENT_DATE
            WHERE (s.frequency = 'DAILY' AND s.last_updated < DATE_SUB(CURDATE(), INTERVAL 1 DAY)) OR 
                  (s.frequency = 'EVERY_TWO_DAYS' AND s.last_updated < DATE_SUB(CURDATE(), INTERVAL 2 DAY)) OR 
                  (s.frequency = 'EVERY_THREE_DAYS' AND s.last_updated < DATE_SUB(CURDATE(), INTERVAL 3 DAY)) OR 
                  (s.frequency = 'WEEKLY' AND s.last_updated < DATE_SUB(CURDATE(), INTERVAL 7 DAY)) OR 
                  (s.frequency = 'BIWEEKLY' AND s.last_updated < DATE_SUB(CURDATE(), INTERVAL 14 DAY)) OR 
                  (s.frequency = 'MONTHLY' AND s.last_updated < DATE_SUB(CURDATE(), INTERVAL 1 MONTH))
            AND s.current_streak_days > 0
            LIMIT :batchSize
            """, nativeQuery = true)
    int resetMissedStreaks(@Param("batchSize") int batchSize);
}
