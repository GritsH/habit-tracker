package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.model.response.HabitResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDate.now())")
    @Mapping(target = "user", ignore = true)
    Habit toHabit(CreateHabitRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateHabit(UpdateHabitRequest request, @MappingTarget Habit habit);

    HabitResponse toDto(Habit habit);

    List<HabitResponse> toDtoList(List<Habit> habits);
}
