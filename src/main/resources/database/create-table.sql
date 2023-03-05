-- liquibase formatted sql

-- changeset agatarauzer:1
CREATE TABLE owners (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL, 
	name VARCHAR(50),
	owner_type VARCHAR(20)
);

-- changeset agatarauzer:2
CREATE TABLE users (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL, 
	email VARCHAR(30) NOT NULL, 
	username VARCHAR(30) NOT NULL, 
	password VARCHAR(150) NOT NULL, 
	enabled BOOLEAN, 
	registration_date DATE,
	owner_id BIGINT,
	FOREIGN KEY (owner_id) REFERENCES owners(id)
);

-- changeset agatarauzer:3
CREATE TABLE roles (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	name VARCHAR(50)
);

-- changeset agatarauzer:4
CREATE TABLE user_roles (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- changeset agatarauzer:5
CREATE TABLE confirmation_tokens (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	confirmation_token VARCHAR(250),
	created_date DATE,
	user_id BIGINT NOT NULL, 
	FOREIGN KEY (user_id) REFERENCES users(id)
);

-- changeset agatarauzer:6
CREATE TABLE books (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	title VARCHAR(250) NOT NULL,
	authors VARCHAR(250),
	isbn VARCHAR(250),
	publisher VARCHAR(250),
	publishing_date VARCHAR(20),
	language VARCHAR(20),
	pages INT(5),
	description LONGTEXT, 
	image_link VARCHAR(250),
	version VARCHAR(20),
	creation_date DATE,
	owner_id BIGINT NOT NULL,
	FOREIGN KEY (owner_id) REFERENCES owners(id)
);

-- changeset agatarauzer:7
CREATE TABLE activities_on_book
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL
);

-- changeset agatarauzer:8
CREATE TABLE readings (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	status VARCHAR(20),
	progress INT(5),
	rate INT(1),
	notes VARCHAR(250)
);

-- changeset agatarauzer:9
CREATE TABLE rentals (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	status VARCHAR(20),
	name VARCHAR(250),
	predicted_return_date DATE,
	notes VARCHAR(250)
);

-- changeset agatarauzer:10
CREATE TABLE purchases (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	price DOUBLE,
	bookstore VARCHAR(100)
);

-- changeset agatarauzer:11
CREATE TABLE book_transfers (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	book_id BIGINT NOT NULL,
	transfer_type VARCHAR(20),
	transfer_date DATE,
	transfer_status VARCHAR(20),
    activity_id BIGINT,
	FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (activity_id) REFERENCES activities_on_book(id)
);