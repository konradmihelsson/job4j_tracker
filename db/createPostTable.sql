create table post
(
    id serial primary key not null,
    name varchar(2000),
    description text,
    link varchar(3000) unique,
    created timestamp
);
