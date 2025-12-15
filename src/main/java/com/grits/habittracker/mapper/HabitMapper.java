package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.model.response.HabitResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HabitMapper {

    Habit createDtoToEntity(CreateHabitRequest request);

    Habit updateDtoToEntity(UpdateHabitRequest request);

    HabitResponse entityToDto(Habit habit);
}
