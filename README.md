# MyBooks :book:


## Table of contents
* [General Information](#general-information)
* [Description](#description)
* [Technologies](#technologies)
* [Setup](#setup)
* [Status](#status)
* [Important changes](#important-changes)
* [In progress](#in-progress)
* [Inspiration](#inspiration)


## General Information
MyBooks is a learning project, to train and develop my programming skills.
It gives me a chance to use in practice features of Spring framework (especially Spring Data JPA, Spring Security), Hibernate and build complex REST application.

## Description
MyBooks is a virtual home library. It helps to store book titles with additional informations. 
So the user can gather data about:
- basic book information (title, authors, publishing year, description etc),
- book reading (is book read, start and end dates, rate, user notes etc),
- book rents (is book lended or borrowed, from/to who, start and end dates),
- book purchase (price, date of purchase etc).

App gives possibility to search titles via GoogleBooks, and automatically add book's information from this source. 
In next step, user can fill missing data about book. Main functionalities are concentrated on adding customized data by user.

## Technologies
Project is created with:
- Java 17
- Spring Boot 2.7
- Spring Data JPA 2.7 
- Hibernate
- MySQL and H2 database
- Spring Security and JWT
- JUnit5 and Mockito
- Liquibase
- Maven


## Setup
To run this project, clone the repository with
```
$ https://github.com/agatarauzer/My-Books-Api.git
```
Create MySQL database and configure it in application.properties

## Status
In progress. New functionalities are systematically added.

## Important changes 
✔ Classes model is based on Book class and additional classes, modeling storage of book and book's connected information 
(about reading, rentals and purchases).

✔ CRUD features: adding books to virtual home library, getting all gathered books, updating and deleting single book.

✔ REST API

✔ Searching of books via GoogleBooks (add external REST API).

✔ Exception handling: @ControllerAdvice and custom exceptions.

✔ Unit testing for each level: mappers, services, controllers, Rest client.

✔ Spring Security with JWT: user authentication (sign-up, sign-in) and authorization (3 roles: admin, user with limited access, user full access - paid version).

✔ User registration: sending confirmation e-mail.

✔ Paginating and sorting.


## In progress
🔖 Statistics for admin: books per user, total number of books, number of users.

🔖 Statistics for user: books purchases - how many, costs per month/year. 

🔖 Spring Validation.

🔖 Integration tests (with H2 database).

🔖 Books whishlist.

🔖 Adding e-mail reminder about date of returning lended/borrowed book.

🔖 Simple recommendation system.

🔖 Deployment (Mogenius).

🔖 Frontend with Thymeleaf.

🔖 Fixing bugs.

## Inspiration
The app is inspired by Bookshelf application.


