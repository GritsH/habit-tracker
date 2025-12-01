package com.grits.habittracker.entity.habit;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class HabitCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String categoryName;
}
