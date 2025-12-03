package com.grits.habittracker.model;

import java.util.Date;
import java.util.UUID;

public class HabitDto {

    private UUID id;

    private String name;

    private Date createdAt;

    private String description;

    private UUID userId;

    private String frequency;

    private String habitCategory;
}
