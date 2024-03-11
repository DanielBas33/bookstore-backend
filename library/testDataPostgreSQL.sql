-- Script to insert dummy dev data into the database.

-- You first need to register two users into the system before running this scirpt.

-- Variable declarations
DO $$
DECLARE
    userId1 INT;
    userId2 INT;
    author1 INT;
    author2 INT;
    author3 INT;
    book1 VARCHAR;
    book2 VARCHAR;
    book3 VARCHAR;
    book4 VARCHAR;
    book5 VARCHAR;
    order1 INT;
    order2 INT;
    order3 INT;
    order4 INT;
    order5 INT;
BEGIN
    -- Replace the id here with the first user id you want to have ownership of the orders.
    userId1 := 1;
    -- Replace the id here with the second user id you want to have ownership of the orders.
    userId2 := 2;

DELETE FROM book_order_quantities;
DELETE FROM book_orders;
DELETE FROM authors;
DELETE FROM inventory;
DELETE FROM books;

INSERT INTO authors (id, name, age) VALUES (nextval('author_id_seq'), 'Author #1', 48);
INSERT INTO authors (id, name, age) VALUES (nextval('author_id_seq'), 'Author #2', 35);
INSERT INTO authors (id, name, age) VALUES (nextval('author_id_seq'), 'Author #3', 26);


SELECT id INTO author1 FROM authors WHERE name = 'Author #1';
SELECT id INTO author2 FROM authors WHERE name = 'Author #2';
SELECT id INTO author3 FROM authors WHERE name = 'Author #3';

INSERT INTO books (isbn, title, author_id, price) VALUES ('Book #1', 'Book one short description.', author1, 5.50);
INSERT INTO books (isbn, title, author_id, price) VALUES ('Book #2', 'Book two short description.', author1, 10.56);
INSERT INTO books (isbn, title, author_id, price) VALUES ('Book #3', 'Book three short description.', author2, 2.74);
INSERT INTO books (isbn, title, author_id, price) VALUES ('Book #4', 'Book four short description.', author2, 15.69);
INSERT INTO books (isbn, title, author_id, price) VALUES ('Book #5', 'Book five short description.', author3, 42.59);

SELECT isbn INTO book1 FROM books WHERE isbn = 'Book #1';
SELECT isbn INTO book2 FROM books WHERE isbn = 'Book #2';
SELECT isbn INTO book3 FROM books WHERE isbn = 'Book #3';
SELECT isbn INTO book4 FROM books WHERE isbn = 'Book #4';
SELECT isbn INTO book5 FROM books WHERE isbn = 'Book #5';

INSERT INTO inventory (id, book_id, quantity) VALUES (nextval('inventory_id_seq'), book1, 5);
INSERT INTO inventory (id, book_id, quantity) VALUES (nextval('inventory_id_seq'), book2, 8);
INSERT INTO inventory (id, book_id, quantity) VALUES (nextval('inventory_id_seq'), book3, 12);
INSERT INTO inventory (id, book_id, quantity) VALUES (nextval('inventory_id_seq'), book4, 73);
INSERT INTO inventory (id, book_id, quantity) VALUES (nextval('inventory_id_seq'), book5, 2);

INSERT INTO book_orders (id, user_id) VALUES (nextval('book_order_id_seq'), userId1);
INSERT INTO book_orders (id, user_id) VALUES (nextval('book_order_id_seq'), userId1);
INSERT INTO book_orders (id, user_id) VALUES (nextval('book_order_id_seq'), userId1);
INSERT INTO book_orders (id, user_id) VALUES (nextval('book_order_id_seq'), userId2);
INSERT INTO book_orders (id, user_id) VALUES (nextval('book_order_id_seq'), userId2);


-- Replace "web_order" and "web_order_quantities" with the actual table names
SELECT id INTO order1 FROM book_orders WHERE user_id = userId1 ORDER BY id DESC LIMIT 1;
SELECT id INTO order2 FROM book_orders WHERE user_id = userId1 ORDER BY id DESC OFFSET 1 LIMIT 1;
SELECT id INTO order3 FROM book_orders WHERE user_id = userId1 ORDER BY id DESC OFFSET 2 LIMIT 1;
SELECT id INTO order4 FROM book_orders WHERE user_id = userId2 ORDER BY id DESC LIMIT 1;
SELECT id INTO order5 FROM book_orders WHERE user_id = userId2 ORDER BY id DESC OFFSET 1 LIMIT 1;

INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order1, book1, 5);
INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order1, book2, 5);
INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order2, book3, 5);
INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order2, book2, 5);
INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order2, book5, 5);
INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order3, book3, 5);
INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order4, book4, 5);
INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order4, book2, 5);
INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order5, book3, 5);
INSERT INTO book_order_quantities (id, order_id, book_id, quantity) VALUES (nextval('book_order_quantity_id_seq'), order5, book1, 5);
END $$;
