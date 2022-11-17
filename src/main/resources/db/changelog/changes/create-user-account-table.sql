--liquibase formatted sql
--changeset <verbanov>:<create-user-account-table>
CREATE TABLE IF NOT EXISTS users
(
    id BIGINT NOT NULL,
    user_name character varying (256) NOT NULL,
    password character varying (256) NOT NULL,
    first_name character varying (256) NOT NULL,
    last_name character varying (256) NOT NULL,
    status character varying (256) NOT NULL,
    created_at timestamp (256) NOT NULL,
    CONSTRAINT user_account_pk PRIMARY KEY (id)
);

--rollback DROP TABLE users;