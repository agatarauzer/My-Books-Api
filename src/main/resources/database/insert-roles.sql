--liquibase formatted sql
-- changeset agatarauzer:12

insert into roles(name) values ('USER');
insert into roles(name) values ('ADMIN');
