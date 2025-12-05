create table habittracker.habit_completion (
    id           varchar(36) primary key default (UUID()),
    created_at   datetime,
    completed_at datetime,
    is_skipped   boolean,
    habit_id     varchar(36) not null,
    foreign key (habit_id) references habittracker.habit (id)
);