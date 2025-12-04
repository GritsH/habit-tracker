package com.grits.habittracker.entity.habit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "habit_completion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "completed_at")
    private LocalDate completedAt;

    @Column(name = "is_skipped")
    private boolean isSkipped;

    @ManyToOne
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;
}