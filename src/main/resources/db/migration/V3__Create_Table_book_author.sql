create table book_author (
        author_id bigint not null,
        book_id bigint not null,
        PRIMARY KEY (Book_Id, Author_Id),
        FOREIGN KEY (Book_Id) REFERENCES book(id),
        FOREIGN KEY (Author_Id) REFERENCES author(id)
    );