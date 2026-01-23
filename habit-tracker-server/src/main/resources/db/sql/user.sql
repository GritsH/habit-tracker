create table habittracker.user (
    id         varchar(36) primary key default (UUID()),
    first_name varchar(20),
    last_name  varchar(20),
    email      varchar(50) unique,
    username   varchar(20) unique,
    password   varchar(255)
);