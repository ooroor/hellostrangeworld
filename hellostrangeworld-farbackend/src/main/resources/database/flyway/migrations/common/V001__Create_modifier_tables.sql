CREATE TABLE IF NOT EXISTS modifier (
    id       INTEGER       NOT NULL,
    modifier VARCHAR2(255) NOT NULL,

    CONSTRAINT pk_modifier PRIMARY KEY (id)
);

CREATE SEQUENCE seq_modifier;