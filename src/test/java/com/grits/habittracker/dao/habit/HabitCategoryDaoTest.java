package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.habit.HabitCategory;
import com.grits.habittracker.repository.habit.HabitCategoryRepository;
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
class HabitCategoryDaoTest {

    @Mock
    private HabitCategoryRepository repository;

    @InjectMocks
    private HabitCategoryDao habitCategoryDao;

    @Test
    @DisplayName("should get all categories")
    void getAllCategories() {
        HabitCategory category = new HabitCategory("id", "category_name");

        when(repository.findAll()).thenReturn(List.of(category));

        List<HabitCategory> result = habitCategoryDao.getAllCategories();

        assertThat(result).isNotEmpty();
        assertThat(result).contains(category);

        verifyNoMoreInteractions(repository);
    }
}