package com.grits.habittracker.model.type;

import java.time.LocalDate;
import java.util.function.Function;

public enum FrequencyType {

    DAILY(d -> d.minusDays(1)),
    EVERY_TWO_DAYS(d -> d.minusDays(2)),
    EVERY_THREE_DAYS(d -> d.minusDays(3)),
    WEEKLY(d -> d.minusWeeks(1)),
    BIWEEKLY(d -> d.minusWeeks(2)),
    MONTHLY(d -> d.minusMonths(1));

    private final Function<LocalDate, LocalDate> missedCheckDate;

    FrequencyType(Function<LocalDate, LocalDate> missedCheckDate) {
        this.missedCheckDate = missedCheckDate;
    }

    public boolean wasMissed(LocalDate lastUpdated, LocalDate yesterday) {
        return !lastUpdated.equals(missedCheckDate.apply(yesterday));
    }
}
