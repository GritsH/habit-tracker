create table habittracker.habit_category (
    id            varchar(36) primary key default (UUID()),
    category_name varchar(40)
);