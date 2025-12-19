create table habittracker.habit (
    id                varchar(36) primary key default (UUID()),
    version           bigint not null default 0,
    habit_name        varchar(30),
    created_at        date,
    start_date        date,
    habit_description text,
    frequency         varchar(36) not null,
    category          varchar(36) not null,
    user_id           varchar(36) not null,
    foreign key (user_id) references habittracker.user (id)
);