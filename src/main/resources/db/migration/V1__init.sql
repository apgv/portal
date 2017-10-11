CREATE TABLE person (
  id           INTEGER PRIMARY KEY,
  first_name   VARCHAR(40),
  last_name    VARCHAR(40),
  email        VARCHAR(40),
  phone        VARCHAR(15),
  created_by   VARCHAR(50),
  created_date TIMESTAMP WITH TIME ZONE,
  updated_by   VARCHAR(50),
  updated_date TIMESTAMP WITH TIME ZONE
);


CREATE TABLE membership (
  id           INTEGER PRIMARY KEY,
  type         VARCHAR(25) NOT NULL,
  year         DATE        NOT NULL,
  price        INTEGER     NOT NULL,
  created_by   VARCHAR(50),
  created_date TIMESTAMP WITH TIME ZONE,
  updated_by   VARCHAR(50),
  updated_date TIMESTAMP WITH TIME ZONE
);

CREATE TABLE member (
  id            INTEGER PRIMARY KEY,
  person_id     INTEGER REFERENCES person (id),
  payment_date  TIMESTAMP WITH TIME ZONE,
  membership_id INTEGER,
  created_by    VARCHAR(50),
  created_date  TIMESTAMP WITH TIME ZONE,
  updated_by    VARCHAR(50),
  updated_date  TIMESTAMP WITH TIME ZONE
);
