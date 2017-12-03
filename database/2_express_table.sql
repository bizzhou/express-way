
DROP DATABASE IF EXISTS express_way;
CREATE DATABASE express_way;
USE express_way;

CREATE TABLE Person (
  id         INTEGER      NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(50)  NOT NULL,
  last_name  VARCHAR(50)  NOT NULL,
  address    VARCHAR(100) NOT NULL,
  city       VARCHAR(50)  NOT NULL,
  state      VARCHAR(50)  NOT NULL,
  zip_code   INTEGER      NOT NULL,
  PRIMARY KEY (id),
  CHECK (id > 0),
  CHECK (zip_code > 0)
);


DROP TABLE IF EXISTS User;
CREATE TABLE User(

  username VARCHAR(40) NOT NULL ,
  password VARCHAR(256) NOT NULL ,
  role VARCHAR(20) NOT NULL,
  person_id INTEGER NOT NULL,

  PRIMARY KEY (username, password),

  FOREIGN KEY (person_id) REFERENCES Person(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


DROP TABLE IF EXISTS Airline;
CREATE TABLE Airline (
  airline_id   CHAR(2),
  airline_name VARCHAR(20) NOT NULL,

  PRIMARY KEY (airline_id)
);

DROP TABLE IF EXISTS AdvPurchaseDiscount;
CREATE TABLE AdvPurchaseDiscount (
  airline_id    CHAR(2),
  days          INTEGER        NOT NULL,
  discount_rate NUMERIC(10, 2) NOT NULL,

  PRIMARY KEY (airline_id, days),
  FOREIGN KEY (airline_id) REFERENCES Airline (airline_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CHECK (days > 0),
  CHECK (discount_rate > 0 AND discount_rate < 100)
);

DROP TABLE IF EXISTS Airport;
CREATE TABLE Airport (
  airport_id   CHAR(3),
  airport_name VARCHAR(128) NOT NULL,
  city         VARCHAR(50),
  country      VARCHAR(50),

  PRIMARY KEY (airport_id)
);

DROP TABLE IF EXISTS Flight;
CREATE TABLE Flight (
  flight_number    INT     NOT NULL,
  airline          CHAR(2),
  seating_capacity INT     NOT NULL,
  stops            INT,
  start_time       DATETIME,
  end_time         DATETIME,
  max_length_stay  INTEGER NOT NULL,
  min_length_stay  INTEGER NOT NULL,
  date_of_week     CHAR(7) NOT NULL, -- formart: 0000000

  -- Modification: deleted departure/arrival airports because `Leg` contains these information

  PRIMARY KEY (flight_number, airline),
  FOREIGN KEY (airline) REFERENCES Airline (airline_id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,

  CHECK (seating_capacity > 0),
  CHECK (max_length_stay > min_length_stay),
  CHECK (min_length_stay >= 0)

);

DROP TABLE IF EXISTS Legs;
CREATE TABLE Legs (
  airline_id     CHAR(2),
  flight_number  INTEGER  NOT NULL,
  leg_number     INTEGER  NOT NULL,

  from_airport   CHAR(3)  NOT NULL, -- airport_id
  to_airport     CHAR(3)  NOT NULL, -- airport_id
  departure_time DATETIME NOT NULL,
  arrival_time   DATETIME NOT NULL,
  actual_departure_time DATETIME,
  actual_arrival_time   DATETIME,


  PRIMARY KEY (flight_number, airline_id, leg_number),
  UNIQUE (flight_number, airline_id, from_airport),

  FOREIGN KEY (from_airport) REFERENCES Airport (airport_id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
  FOREIGN KEY (to_airport) REFERENCES Airport (airport_id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
  FOREIGN KEY (flight_number, airline_id) REFERENCES Flight (flight_number, airline)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,

  CHECK (leg_number > 0)
);


DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer (

  account_number      VARCHAR(20) NOT NULL,
  id                  INTEGER     NOT NULL,

  username            VARCHAR(40) NOT NULL,
  account_create_date DATETIME DEFAULT CURRENT_TIMESTAMP,

  credit_card         VARCHAR(20),
  telephone           VARCHAR(20),
  email               VARCHAR(20),
  rating              INTEGER,

  PRIMARY KEY (account_number),


  FOREIGN KEY (id) REFERENCES Person (id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,

  FOREIGN KEY (username) REFERENCES User (username)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,

  CHECK (rating >= 0 AND rating <= 10)

);

DROP TABLE IF EXISTS Passengers;
CREATE TABLE Passengers (
  id                 INTEGER NOT NULL,
  account_number     VARCHAR(20) NOT NULL,
  telephone          VARCHAR(12),
  email              VARCHAR(20),

  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES Person (id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,

  FOREIGN KEY (account_number) REFERENCES Customer(account_number)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

DROP TABLE IF EXISTS Employee;
CREATE TABLE Employee (
  ssn         INT,
  id          INTEGER        NOT NULL,

  username    VARCHAR(40)    NOT NULL,
  is_manager  BOOLEAN        NOT NULL,
  start_date  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  hourly_rate NUMERIC(10, 2) NOT NULL,
  telephone   VARCHAR(20),

  PRIMARY KEY (ssn),
  FOREIGN KEY (id) REFERENCES Person (id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (username) REFERENCES User (username)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,

  UNIQUE (id),
  CHECK (ssn > 0),
  CHECK (hourly_rate > 0)
);


DROP TABLE IF EXISTS Reservations;
CREATE TABLE Reservations (
  reservation_number INTEGER AUTO_INCREMENT,
  account_number     VARCHAR(20) NOT NULL,
  reservation_date   DATETIME DEFAULT CURRENT_TIMESTAMP,
  total_fare         NUMERIC(10, 2) NOT NULL,
  booking_fee        NUMERIC(10, 2) NOT NULL,
  customer_rep_ssn   INT,

  PRIMARY KEY (reservation_number),

  FOREIGN KEY (customer_rep_ssn) REFERENCES Employee (ssn)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,

  FOREIGN KEY (account_number) REFERENCES Customer (account_number)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS Customer_Preferences;
CREATE TABLE Customer_Preferences (
  account_number VARCHAR(20),
  seat           VARCHAR(6) DEFAULT 'aisle',
  meal           VARCHAR(30),

  PRIMARY KEY (account_number),
  FOREIGN KEY (account_number) REFERENCES Customer (account_number)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT SeatConstraINT CHECK (seat IN ('aisle', 'window'))
);

DROP TABLE IF EXISTS Include;
CREATE TABLE Include (
  reservation_number INTEGER,
  airline_id         CHAR(2),
  flight_number      INTEGER,
  leg_number         INTEGER,
  passenger_lname    CHAR(30),
  passenger_fname    CHAR(30),
  dept_date          DATE NOT NULL,
  seat_number        CHAR(5),
  class              CHAR(10),
  meal               CHAR(20),
  from_stop_num      INTEGER,

  PRIMARY KEY (reservation_number, airline_id, flight_number, leg_number),
  FOREIGN KEY (reservation_number) REFERENCES Reservations (reservation_number)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (flight_number, airline_id, leg_number) REFERENCES Legs (flight_number, airline_id, leg_number)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Auctions;
CREATE TABLE Auctions (
  account_num        VARCHAR(20),
#   reservation_number INTEGER NOT NULL ,
  airline_id         CHAR(2),
  flight_num         INT,
  leg_number         INTEGER        NOT NULL,
  class              VARCHAR(20),
  dept_date          DATETIME,
  NYOP               NUMERIC(10, 2) NOT NULL,
  is_accepted        BOOLEAN DEFAULT FALSE ,

  PRIMARY KEY (account_num, airline_id, flight_num, class, dept_date),
  FOREIGN KEY (account_num) REFERENCES Customer (account_number)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
  FOREIGN KEY (flight_num, airline_id) REFERENCES Flight (flight_number, airline)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
  CHECK (NYOP > 0)
);


DROP TABLE IF EXISTS Fare;
CREATE TABLE Fare (

  airline_id    CHAR(2)        NOT NULL,
  flight_number INTEGER        NOT NULL,
  leg_number    INTEGER        NOT NULL,
  fare_type     VARCHAR(20)    NOT NULL,
  class         VARCHAR(20)    NOT NULL,
  fare          NUMERIC(10, 2) NOT NULL,

  PRIMARY KEY (airline_id, flight_number, fare_type, class, leg_number),
  FOREIGN KEY (flight_number, airline_id) REFERENCES Flight (flight_number, airline)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

  CHECK (fare > 0)

);

