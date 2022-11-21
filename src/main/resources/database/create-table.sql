-- liquibase formatted sql

-- changeset agatarauzer:1
CREATE TABLE users (
	user_id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL, 
	first_name VARCHAR(50), 
	last_name VARCHAR(50), 
	email VARCHAR(30) NOT NULL, 
	username VARCHAR(30) NOT NULL, 
	password VARCHAR(150) NOT NULL, 
	enabled BOOLEAN, 
	registration_date DATE 
);

-- changeset agatarauzer:2
CREATE TABLE roles (
	role_id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	name VARCHAR(50)
);

-- changeset agatarauzer:3
CREATE TABLE user_roles (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(user_id),
	FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- changeset agatarauzer:4
CREATE TABLE confirmation_tokens (
	id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	confirmation_token VARCHAR(250),
	created_date DATE,
	user_id BIGINT NOT NULL, 
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- changeset agatarauzer:5
CREATE TABLE readings (
	reading_id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	status VARCHAR(20),
	start_date DATE,
	end_date DATE,
	readed_pages INT(3),
	rate INT(1),
	notes VARCHAR(250)
);

-- changeset agatarauzer:6
CREATE TABLE rentals (
	rental_id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	status VARCHAR(20),
	name VARCHAR(250),
	start_date DATE,
	end_date DATE,
	notes VARCHAR(250)
);

-- changeset agatarauzer:7	
CREATE TABLE purchases (
	purchase_id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	price DOUBLE,
	purchase_date DATE,
	bought_from VARCHAR(100)
);

-- changeset agatarauzer:8
CREATE TABLE books (
	book_id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
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
	copies INT(2),
	creation_date DATE,
	reading_id BIGINT,
	rental_id BIGINT,
	purchase_id BIGINT,
	user_id BIGINT,
	FOREIGN KEY (reading_id) REFERENCES readings(reading_id),
	FOREIGN KEY (rental_id) REFERENCES rentals(rental_id),
	FOREIGN KEY (purchase_id) REFERENCES purchases(purchase_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);






	
