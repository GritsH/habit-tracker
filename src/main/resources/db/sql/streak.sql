create table habittracker.streak (
    id int not null auto_increment primary key,
    current_streak_days int,
    longest_streak_days int,
    habit_id int,
    foreign key (habit_id) references habittracker.habit(id)
);