package com.grits.habittracker.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogCompletionRequest {

    @NotBlank
    private String habitId;

    @NotNull
    private LocalDate completedAt;

    @NotBlank
    private boolean isSkipped;
}
