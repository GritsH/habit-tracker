package com.grits.habittracker.entity.habit;

import com.grits.habittracker.entity.User;
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

import java.util.Date;

@Entity
@Table(name = "habit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "habit_name")
    private String name;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "habit_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "frequency_id", nullable = false)
    private HabitFrequency frequency;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private HabitCategory habitCategory;
}