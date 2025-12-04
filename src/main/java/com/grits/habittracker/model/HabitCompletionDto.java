package com.grits.habittracker.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class HabitCompletionDto {

    private HabitDto habit;

    private LocalDate completedAt;

    private boolean isSkipped;

    public static HabitCompletionDto fromEntity(){
        return HabitCompletionDto.builder().build();
    }

}
