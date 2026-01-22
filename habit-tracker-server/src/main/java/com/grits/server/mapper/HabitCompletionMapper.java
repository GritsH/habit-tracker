package com.grits.server.mapper;

import com.grits.server.entity.habit.HabitCompletion;
import com.grits.api.model.response.HabitCompletionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitCompletionMapper {

    @Mapping(target = "loggedAt", expression = "java(LocalDate.now())")
    HabitCompletion toEntity(String habitId);

    HabitCompletionResponse toResponse(HabitCompletion completion);

    List<HabitCompletionResponse> toDtoList(List<HabitCompletion> habits);
}
