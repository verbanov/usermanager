--liquibase formatted sql
--changeset <verbanov>:<insert-user-role-values>

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);