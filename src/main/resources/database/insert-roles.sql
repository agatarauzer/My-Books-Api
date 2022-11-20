--liquibase formatted sql
-- changeset agatarauzer:9

insert into roles(role_id, name) values (1, 'ROLE_USER_PAID');
insert into roles(role_id, name) values (2, 'ROLE_USER_LIMITED');
insert into roles(role_id, name) values (3, 'ROLE_ADMIN');
