create table author (
        id bigint generated by default as identity,
        name varchar(255),
        primary key (id)
    );