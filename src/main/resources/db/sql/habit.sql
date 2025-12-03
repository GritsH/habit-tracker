create table habittracker.habit (
    id int not null auto_increment primary key,
    habit_name varchar(30),
    created_at date,
    habit_description text,
    frequency_id int,
    habit_category_id int,
    user_id int,
    foreign key (frequency_id) references habittracker.habit_frequency(id),
    foreign key (habit_category_id) references habittracker.habit_category(id),
    foreign key (user_id) references habittracker.user(id)
);