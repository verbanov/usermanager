--liquibase formatted sql
--changeset <verbanov>:<create-user-account-sequence-id>

CREATE SEQUENCE IF NOT EXISTS public.user_id_seq INCREMENT 1 START 3 MINVALUE 3;

--rollback DROP SEQUENCE public.user_id_seq;