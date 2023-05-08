CREATE TABLE client
(
    id            SERIAL PRIMARY KEY,
    phoneNumber   CHARACTER VARYING(11)  NOT NULL UNIQUE ,
    password      CHARACTER VARYING(60)  NOT NULL,
    full_name     CHARACTER VARYING(255) NOT NULL,
    birthday_date DATE                   NOT NULL,
    GENDER        CHARACTER VARYING(8)   NOT NULL
);

CREATE UNIQUE INDEX client_PK on client (id);
CREATE UNIQUE INDEX client_phone_number_idx on client(phoneNumber);

CREATE TABLE visit
(
    id        SERIAL PRIMARY KEY,
    arrival   TIMESTAMP NOT NULL,
    leaving   TIMESTAMP,
    client_id INTEGER REFERENCES client (id)
);

CREATE UNIQUE INDEX visit_PK on visit (id);

CREATE TABLE club_card
(
    id           SERIAL PRIMARY KEY,
    active_since DATE    NOT NULL,
    active_until DATE    NOT NULL,
    is_active    BOOLEAN NOT NULL,
    client_id    INTEGER REFERENCES client (id)
);

CREATE UNIQUE INDEX club_card_PK on club_card (id);