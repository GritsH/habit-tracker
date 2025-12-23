package com.grits.habittracker.entity;

import com.grits.habittracker.entity.habit.Habit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;


@Entity
@Table(name = "streak")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Streak {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(
            name = "id",
            length = 36,
            updatable = false,
            nullable = false,
            unique = true
    )
    private String id;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    @Column(name = "current_streak_days")
    private Integer currentStreak = 0;

    @Column(name = "longest_streak_days")
    private Integer longestStreak = 0;

    @OneToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;
}