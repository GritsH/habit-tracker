package com.grits.habittracker.entity.habit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "habit_frequency")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class HabitFrequency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "frequency_name")
    private String name;
}
