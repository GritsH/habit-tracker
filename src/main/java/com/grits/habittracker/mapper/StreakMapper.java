package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.model.response.StreakResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StreakMapper {

    StreakResponse toDto(Streak streak);
}
