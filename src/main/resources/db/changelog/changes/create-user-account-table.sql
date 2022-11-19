--liquibase formatted sql
--changeset <verbanov>:<create-user-account-table>
CREATE TABLE IF NOT EXISTS users
(
    id BIGINT NOT NULL,
    user_name character varying (20) NOT NULL,
    password character varying (60) NOT NULL,
    first_name character varying (20) NOT NULL,
    last_name character varying (20) NOT NULL,
    status character varying (8) NOT NULL,
    created_at timestamp (26) NOT NULL,
    CONSTRAINT user_account_pk PRIMARY KEY (id)
);

--rollback DROP TABLE users;