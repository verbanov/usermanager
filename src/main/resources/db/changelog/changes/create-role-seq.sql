--liquibase formatted sql
--changeset <verbanov>:<create-role-sequence-id>

CREATE SEQUENCE IF NOT EXISTS public.role_id_seq INCREMENT 1 START 3 MINVALUE 3;

--rollback DROP SEQUENCE public.role_id_seq;