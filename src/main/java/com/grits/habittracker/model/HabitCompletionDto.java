package com.grits.habittracker.model;

import java.util.Date;
import java.util.UUID;

public class HabitCompletionDto {

    private UUID habitId;

    private Date completedAt;

    private boolean isSkipped;

}
