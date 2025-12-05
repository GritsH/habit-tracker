create table habittracker.habit (
    id                varchar(36) primary key default (UUID()),
    habit_name        varchar(30),
    created_at        date,
    start_date        date,
    habit_description text,
    frequency_id      varchar(36) not null,
    habit_category_id varchar(36) not null,
    user_id           varchar(36) not null,
    foreign key (frequency_id) references habittracker.habit_frequency (id),
    foreign key (habit_category_id) references habittracker.habit_category (id),
    foreign key (user_id) references habittracker.user (id)
);