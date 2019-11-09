CREATE TABLE greeting_description (
    id        INTEGER       NOT NULL,
    adjective VARCHAR2(255) NOT NULL,

    CONSTRAINT pk_greeting_description PRIMARY KEY (id)
);

CREATE SEQUENCE seq_greeting_description;