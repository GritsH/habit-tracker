package com.grits.habittracker.entity;

import com.grits.habittracker.entity.habit.Habit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "streak")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Streak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "current_streak_days")
    private Integer currentStreak;

    @Column(name = "longest_streak_days")
    private Integer longestStreak;

    @OneToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;
}