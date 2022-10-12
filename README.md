# MyBooks


## Table of contents
* [General Information](#general-information)
* [Description](#description)
* [Technologies](#technologies)
* [Setup](#setup)
* [Status](#status)
* [Important changes](#important-changes)
* [Plans](#plans)
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
- REST
- MySQL database
- Spring Security and JWT
- JUnit5 and Mockito testing
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
1. Classes model is based on Book class and additional classes, modeling storage of book connected information 
(about reading, rentals and purchases).
2. Basic CRUD project: add books to virtual home library, get all gathered books, update and delete single book.
3. Searching of books via GoogleBooks (add external REST API)
4. Mapping searching result into Book class.
5. Exception handling: add @ControllerAdvice and custom exceptions.
6. Unit testing for each level: mappers, services, controllers, Rest client.
7. Spring Security: add user authentication (signup, signin) and authorization (3 roles: admin, user with limited access, user full access - paid version)

## Plans
- Statistics for admin: books per user, total number of books, number of users.
- Statistics for user: books purchases - how many, costs per month/year. 
- Sorting and paginating (search result, user book list).
- Books whishlist.
- Adding e-mail reminder about date of returning lended/borrowed book.
- Simple recommendation system.
- Integration testing.
- Deployment on Heroku.

## Inspiration
The app is inspired by Bookshelf application.


