package com.grits.api.model.response;

import com.grits.api.model.type.CategoryType;
import lombok.Value;

import java.time.LocalDate;

@Value
public class HabitResponse {

    String id;

    Long version;

    String name;

    LocalDate createdAt;

    LocalDate startDate;

    String description;

    CategoryType category;

}
