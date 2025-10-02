-- FILE: 02_mock_data_insert.sql
-- (Execute this file after executing the 01_schema_creation.sql file)

USE university_library;

-- 1. Insert data into table categories
INSERT INTO categories (id, name) VALUES
(1, 'Fiction'),
(2, 'Programming'),
(3, 'Classic Literature'),
(4, 'Science'),
(5, 'History');

-- 2. Insert data into table books
INSERT INTO books (category_id, title, author, isbn, description, total_copies, available_copies) VALUES
(3, 'The Catcher in the Rye', 'J.D. Salinger', '9780316769174', 'A story about teenage angst and alienation in post-WW2 America.', 5, 3),
(3, 'To Kill a Mockingbird', 'Harper Lee', '9780061120084', 'A story about injustice and childhood innocence in the American South.', 3, 3),
(1, '1984', 'George Orwell', '9780451524935', 'A dystopian social science fiction novel.', 7, 5),
(2, 'Java Programming Essentials', 'John Smith', '9781234567890', 'A beginner''s guide to core Java concepts.', 10, 10),
(5, 'A Short History of Nearly Everything', 'Bill Bryson', '9780767908170', 'Explores science and the history of scientific discoveries.', 6, 6),
(1, 'The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 'A classic novel of the Jazz Age.', 4, 2);

-- 3. Insert data into table students
INSERT INTO students (id, code, name, grade, email) VALUES
(1, 'S001', 'Alice Johnson', '10', 'alice.j@uni.edu'),
(2, 'S002', 'Bob Williams', '11', 'bob.w@uni.edu'),
(3, 'S003', 'Charlie Brown', '10', 'charlie.b@uni.edu'),
(4, 'S004', 'Dana Scully', '12', 'dana.s@uni.edu');

-- 4. Insert data into table loans
-- 4.1: Alice loans the Catcher in the Rye (ID 1) - not return yet 
INSERT INTO loans (book_id, student_id, loan_date, due_date, return_date) VALUES
(1, 1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), NULL);

-- 4.2: Bob loans 1984 (ID 3) - has already returned 
INSERT INTO loans (book_id, student_id, loan_date, due_date, return_date) VALUES
(3, 2, DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY), DATE_SUB(CURDATE(), INTERVAL 5 DAY));

-- 4.3: Charlie loans The Great Gatsby (ID 6) - not return yet (Loan ID 3)
INSERT INTO loans (book_id, student_id, loan_date, due_date, return_date) VALUES
(6, 3, DATE_SUB(CURDATE(), INTERVAL 5 DAY), DATE_ADD(CURDATE(), INTERVAL 2 DAY), NULL);

-- 4.4: Dana loans The Great Gatsby (ID 6) - not return yet (Loan ID 4)
INSERT INTO loans (book_id, student_id, loan_date, due_date, return_date) VALUES
(6, 4, DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_ADD(CURDATE(), INTERVAL 5 DAY), NULL);

-- 4.5: Alice loans Java Programming (ID 4) - Over due date (expired) (Loan ID 5)
INSERT INTO loans (book_id, student_id, loan_date, due_date, return_date) VALUES
(4, 1, DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_SUB(CURDATE(), INTERVAL 10 DAY), NULL);
