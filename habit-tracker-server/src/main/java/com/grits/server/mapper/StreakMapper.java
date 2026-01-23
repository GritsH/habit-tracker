package com.grits.server.mapper;

import com.grits.server.entity.Streak;
import com.grits.api.model.response.StreakResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StreakMapper {

    StreakResponse toDto(Streak streak);
}
