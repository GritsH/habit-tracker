package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.habit.HabitCategoryDao;
import com.grits.habittracker.entity.habit.HabitCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitCategoryServiceTest {

    @Mock
    private HabitCategoryDao habitCategoryDao;

    @InjectMocks
    private HabitCategoryService habitCategoryService;

    @Test
    @DisplayName("should retrieve all categories")
    void getAllCategories() {
        HabitCategory category = new HabitCategory("id", "category_name");

        when(habitCategoryDao.getAllCategories()).thenReturn(List.of(category));

        List<HabitCategory> result = habitCategoryService.getAllCategories();

        assertThat(result).isNotEmpty();
        assertThat(result).contains(category);

        verifyNoMoreInteractions(habitCategoryDao);
    }
}