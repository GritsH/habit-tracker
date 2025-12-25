create table habittracker.streak (
    id                  varchar(36) primary key default (UUID()),
    reset_at            datetime,
    current_streak_days int,
    longest_streak_days int,
    habit_id            varchar(36) not null,
    frequency           varchar(36) not null,
    index idx_streak_reset_at(reset_at)
);