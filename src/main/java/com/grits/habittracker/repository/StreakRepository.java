package com.grits.habittracker.repository;

import com.grits.habittracker.entity.Streak;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreakRepository extends JpaRepository<Streak, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Streak s WHERE s.habitId=:habitId")
    Optional<Streak> findByHabitIdWithLock(@Param("habitId") String habitId);

    Optional<Streak> findByHabitId(String id);

    void deleteAllByHabitId(String habitId);

    @Modifying
    @Query(value = """
            UPDATE streak s 
            SET s.current_streak_days = 0
            WHERE s.reset_at < CURRENT_DATE
            AND s.current_streak_days > 0
            LIMIT :batchSize
            """, nativeQuery = true)
    int resetMissedStreaks(@Param("batchSize") int batchSize);
}
