package com.grits.habittracker.entity;

import com.grits.habittracker.entity.habit.Habit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
