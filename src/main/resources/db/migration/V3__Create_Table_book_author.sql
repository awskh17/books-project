create table book_author (
        author_id bigint not null,
        book_id bigint not null,
        primary key (author_id, book_id)
    )
--            alter table if exists book_author
--                  add constraint FKbjqhp85wjv8vpr0beygh6jsgo
--                  foreign key (author_id)
--                  references author
--               alter table if exists book_author
--                  add constraint FKhwgu59n9o80xv75plf9ggj7xn
--                  foreign key (book_id)
--                  references book