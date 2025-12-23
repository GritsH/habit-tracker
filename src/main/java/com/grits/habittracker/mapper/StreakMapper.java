package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.model.response.StreakResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StreakMapper {

    @Mapping(target = "habitId", source = "habit.id")
    StreakResponse toDto(Streak streak);
}
