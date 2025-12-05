create table habittracker.streak (
    id                  varchar(36) primary key default (UUID()),
    current_streak_days int,
    longest_streak_days int,
    habit_id            varchar(36) not null,
    foreign key (habit_id) references habittracker.habit (id)
);