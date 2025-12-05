create table habittracker.habit_frequency (
    id             varchar(36) primary key default (UUID()),
    frequency_name varchar(20)
);