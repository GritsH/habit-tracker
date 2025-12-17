package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCategory;
import com.grits.habittracker.entity.habit.HabitFrequency;
import com.grits.habittracker.model.response.HabitResponse;
import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.model.type.FrequencyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitMapper {

    @Mapping(source = "frequency", target = "frequency", qualifiedByName = "frequencyNameToEnum")
    @Mapping(source = "habitCategory", target = "category", qualifiedByName = "categoryNameToEnum")
    HabitResponse entityToDto(Habit habit);

    List<HabitResponse> entityListToDtoList(List<Habit> habits);

    @Named("frequencyNameToEnum")
    default FrequencyType mapFrequencyNameToEnum(HabitFrequency frequency) {
        if (frequency == null || frequency.getName() == null) {
            return null;
        }
        return FrequencyType.valueOf(frequency.getName());
    }

    @Named("categoryNameToEnum")
    default CategoryType mapCategoryNameToEnum(HabitCategory habitCategory) {
        if (habitCategory == null || habitCategory.getName() == null) {
            return null;
        }
        return CategoryType.valueOf(habitCategory.getName());
    }
}
