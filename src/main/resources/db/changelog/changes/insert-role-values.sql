--liquibase formatted sql
--changeset <verbanov>:<insert-role-values>

INSERT INTO roles (id, role_name) VALUES (1, 'USER');
INSERT INTO roles (id, role_name) VALUES (2, 'ADMIN');