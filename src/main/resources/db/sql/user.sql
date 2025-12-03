create table habittracker.user (
    id         int not null auto_increment primary key,
    first_name varchar(20),
    last_name  varchar(20),
    username   varchar(20),
    email      varchar(30),
    password   varchar(30)
);