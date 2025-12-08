create table habittracker.user (
    id         varchar(36) primary key default (UUID()),
    first_name varchar(20),
    last_name  varchar(20),
    username   varchar(20) unique,
    email      varchar(50) unique,
    password   varchar(255)
);