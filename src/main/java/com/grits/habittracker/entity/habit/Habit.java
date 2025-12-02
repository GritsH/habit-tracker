package com.grits.habittracker.entity.habit;

import com.grits.habittracker.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "habit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
