CREATE TABLE person (
  id           SERIAL PRIMARY KEY,
  original_id  INTEGER                  NOT NULL,
  active       BOOLEAN                  NOT NULL,
  full_name    VARCHAR(54)              NOT NULL,
  email        VARCHAR(40),
  phone        VARCHAR(15),
  created_by   VARCHAR(50)              NOT NULL,
  created_date TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE membership (
  id         SERIAL PRIMARY KEY,
  type       VARCHAR(25)              NOT NULL,
  year       DATE                     NOT NULL,
  price      INTEGER                  NOT NULL,
  start_date TIMESTAMP WITH TIME ZONE NOT NULL,
  end_date   TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE member (
  id            SERIAL PRIMARY KEY,
  original_id   INTEGER                  NOT NULL,
  active        BOOLEAN                  NOT NULL,
  person_id     INTEGER REFERENCES person (id),
  payment_date  TIMESTAMP WITH TIME ZONE NOT NULL,
  membership_id INTEGER                  NOT NULL,
  created_by    VARCHAR(50)              NOT NULL,
  created_date  TIMESTAMP WITH TIME ZONE NOT NULL
);
