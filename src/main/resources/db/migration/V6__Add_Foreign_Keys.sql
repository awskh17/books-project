SET REFERENTIAL_INTEGRITY TRUE;

-- Add foreign key constraint between Book and Publisher
ALTER TABLE book
    ADD CONSTRAINT FK_book_publisher
    FOREIGN KEY (publisher_Id)
    REFERENCES publisher(id);

-- Add the foreign key constraint for the Book_Author join table
ALTER TABLE Book_Author
    ADD CONSTRAINT FK_book_author_book
    FOREIGN KEY (Book_Id)
    REFERENCES book(id);

ALTER TABLE Book_Author
    ADD CONSTRAINT FK_book_author_author
    FOREIGN KEY (Author_Id)
    REFERENCES author(id);