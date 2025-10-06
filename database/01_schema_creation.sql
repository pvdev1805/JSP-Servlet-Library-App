-- FILE: 01_schema_creation.sql

-- 1. Create Database
drop database if exists university_library;
create database university_library;
use university_library;

-- 2. Create table categories
create table categories(
	id int auto_increment,
    name varchar(100) unique not null,
    primary key (id)
);

-- 3. Create table books
create table books (
	id int auto_increment,
    category_id int,
    title varchar(255) not null,
    author varchar(100) not null,
    isbn varchar(20) unique not null,
    description text,
    total_copies int not null,
    available_copies int not null,
    primary key (id),
    foreign key (category_id) references categories(id) on delete restrict,
    check (available_copies <= total_copies)
);

-- 4. Create table students
create table students (
	id int auto_increment,
    user_id int unique not null,
    name varchar(100) not null,
    code varchar(50) unique not null,
    grade varchar(10),
    email varchar(100),
    primary key (id),
    foreign key (user_id) references users(id) on delete cascade
);

-- 5. Create table loans
create table loans (
	id int auto_increment,
    book_id int not null,
    student_id int not null,
    loan_date date not null,
    due_date date not null,
    return_date date,
    primary key (id),
    foreign key (book_id) references books(id) on delete restrict,
    foreign key (student_id) references students(id) on delete restrict,
    check (return_date is null or return_date >= loan_date)
);

-- 6. Create table users
create table users (
	id int auto_increment,
    username varchar(100) unique not null,
    hashed_password varchar(255) not null,
    role varchar(20) not null default 'STUDENT',
    primary key (id)
);

-- 6. Create index (optional) to support query processing
create index idx_book_title on books(title);
create index idx_student_code on students(code);
create index idx_loan_due on loans(due_date); 