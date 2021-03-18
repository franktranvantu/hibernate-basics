CREATE DATABASE hibernate_basics;

CREATE TABLE contacts(
     id INT PRIMARY KEY AUTO_INCREMENT,
     first_name VARCHAR(50),
     last_name VARCHAR(50),
     email VARCHAR(50),
     phone INT(10)
);

INSERT INTO contacts (first_name, last_name, email, phone) VALUES ('Frank', 'Tran', 'franktran@gmail.com', 0123456789);
INSERT INTO contacts (first_name, last_name, email, phone) VALUES ('Henry', 'Tran', 'henrytran@gmail.com', 0123444555);
INSERT INTO contacts (first_name, last_name, email, phone) VALUES ('Bean', 'Nguyen', 'beannguyen@gmail.com', 0123666777);