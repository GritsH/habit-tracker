package com.grits.habittracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grits.habittracker.model.type.FrequencyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @Column(name = "reset_at")
    private LocalDate resetAt;

    @Column(name = "current_streak_days")
    private Integer currentStreak = 0;

    @Column(name = "longest_streak_days")
    private Integer longestStreak = 0;

    @Column(name = "habit_id")
    private String habitId;

    @Enumerated(EnumType.STRING)
    private FrequencyType frequency;
}