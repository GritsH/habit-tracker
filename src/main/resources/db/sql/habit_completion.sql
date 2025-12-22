create table habittracker.habit_completion (
    id             varchar(36) primary key default (UUID()),
    logged_at      datetime,
    completion_key varchar(50) unique,
    habit_id       varchar(36) not null,
    foreign key (habit_id) references habittracker.habit (id)
);