--liquibase formatted sql
--changeset <verbanov>:<insert-user-accounts-values>

INSERT INTO users (id, user_name, password, first_name, last_name, status, created_at)
VALUES (1, 'bob', '$2a$10$LnT3pLUtTatnl1Ze73M3GuVrCIstVlXh778APMxkjyQ6u6LjWQAmm',
        'FirstBob', 'LastBob', 'ACTIVE', '2022-11-16T22:45:41');
INSERT INTO users (id, user_name, password, first_name, last_name, status, created_at)
VALUES (2, 'alice', '$2a$10$OjgQ6CmdWlexj77FY1jNB.N5XJrv9Dbxh1k0cBd7SKDYtu7TeaX4O',
        'Firstalice', 'Lastalice', 'ACTIVE', '2022-11-16T22:44:44');