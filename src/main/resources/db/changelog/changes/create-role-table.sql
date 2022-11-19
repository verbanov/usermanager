--liquibase formatted sql
--changeset <verbanov>:<create-role-table>
CREATE TABLE IF NOT EXISTS roles
(
    id BIGINT NOT NULL,
    role_name character varying (10) NOT NULL,
    CONSTRAINT role_pk PRIMARY KEY (id)
);

--rollback DROP TABLE roles;