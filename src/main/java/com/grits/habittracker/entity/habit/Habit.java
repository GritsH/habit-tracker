package com.grits.habittracker.entity.habit;

import com.grits.habittracker.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "habit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Habit {
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

    @Column(name = "habit_name")
    private String name;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "habit_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "frequency_id", nullable = false)
    private HabitFrequency frequency;

    @ManyToOne
    @JoinColumn(name = "habit_category_id", nullable = false)
    private HabitCategory habitCategory;
}