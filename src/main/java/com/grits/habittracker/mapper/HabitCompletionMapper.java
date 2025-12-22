package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.habit.HabitCompletion;
import com.grits.habittracker.model.response.HabitCompletionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitCompletionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habit", ignore = true)
    @Mapping(target = "completionLog", ignore = true)
    @Mapping(target = "loggedAt", expression = "java(LocalDate.now())")
    HabitCompletion toEntity(String habitId);

    HabitCompletionResponse toResponse(HabitCompletion completion);

    List<HabitCompletionResponse> toDtoList(List<HabitCompletion> habits);
}
