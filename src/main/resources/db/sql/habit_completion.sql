create table habittracker.habit_completion (
    id int not null auto_increment primary key,
    created_at datetime,
    completed_at datetime,
    is_skipped boolean,
    habit_id int,
    foreign key (habit_id) references habittracker.habit(id)
);