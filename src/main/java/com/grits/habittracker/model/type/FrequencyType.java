package com.grits.habittracker.model.type;

import java.time.LocalDate;
import java.util.function.Function;

public enum FrequencyType {

    DAILY(d -> d.plusDays(1)),
    EVERY_TWO_DAYS(d -> d.plusDays(2)),
    EVERY_THREE_DAYS(d -> d.plusDays(3)),
    WEEKLY(d -> d.plusWeeks(1)),
    BIWEEKLY(d -> d.plusWeeks(2)),
    MONTHLY(d -> d.plusMonths(1));
    private final Function<LocalDate, LocalDate> resetAt;

    FrequencyType(Function<LocalDate, LocalDate> resetAt) {
        this.resetAt = resetAt;
    }

    public LocalDate updateResetAt() {
        return resetAt.apply(LocalDate.now());
    }
}
