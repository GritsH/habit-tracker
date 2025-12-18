package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.response.HabitResponse;
import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.model.type.FrequencyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDate.now())")
    @Mapping(target = "user", ignore = true)
    Habit createDtoToEntity(CreateHabitRequest request);

    @Mapping(source = "frequency", target = "frequency", qualifiedByName = "frequencyNameToEnum")
    @Mapping(source = "category", target = "category", qualifiedByName = "categoryNameToEnum")
    HabitResponse entityToDto(Habit habit);

    List<HabitResponse> entityListToDtoList(List<Habit> habits);

    @Named("frequencyNameToEnum")
    default FrequencyType mapFrequencyNameToEnum(String frequency) {
        return FrequencyType.valueOf(frequency);
    }

    @Named("categoryNameToEnum")
    default CategoryType mapCategoryNameToEnum(String habitCategory) {
        return CategoryType.valueOf(habitCategory);
    }
}
