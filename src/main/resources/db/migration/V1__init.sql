CREATE TABLE person (
  id           SERIAL PRIMARY KEY,
  original_id  INTEGER                  NOT NULL,
  active       BOOLEAN                  NOT NULL,
  full_name    VARCHAR(54)              NOT NULL,
  email        VARCHAR(40),
  phone        VARCHAR(15),
  address      VARCHAR(50),
  created_by   VARCHAR(50)              NOT NULL,
  created_date TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE membership_type (
  id     SERIAL PRIMARY KEY,
  active BOOLEAN     NOT NULL,
  type   VARCHAR(25) NOT NULL,
  year   INTEGER     NOT NULL,
  price  INTEGER     NOT NULL
);

CREATE TABLE membership (
  id                 SERIAL PRIMARY KEY,
  original_id        INTEGER                  NOT NULL,
  active             BOOLEAN                  NOT NULL,
  person_id          INTEGER REFERENCES person (id),
  membership_type_id INTEGER REFERENCES membership_type (id),
  payment_date       DATE                     NOT NULL,
  created_by         VARCHAR(50)              NOT NULL,
  created_date       TIMESTAMP WITH TIME ZONE NOT NULL
);
