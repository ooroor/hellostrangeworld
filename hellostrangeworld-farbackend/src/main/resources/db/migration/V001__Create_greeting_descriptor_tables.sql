CREATE TABLE description (
    id                      INTEGER       NOT NULL,
    value                   VARCHAR2(255) NOT NULL,

    CONSTRAINT pk_description   PRIMARY KEY (id)
);

CREATE SEQUENCE seq_description;