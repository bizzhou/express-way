USE express_way;

INSERT INTO `Airline` (airline_id, airline_name)
VALUES ('AB', 'Air Berlin'),
  ('AJ', 'Air Japan'),
  ('AM', 'Air Madagascar'),
  ('AA', 'American Airlines'),
  ('BA', 'British Airways'),
  ('DA', 'Delta Airlines'),
  ('JA', 'JetBlue Airways'),
  ('LA', 'Lufthansa'),
  ('SA', 'Southwest Airlines'),
  ('UA', 'United Airlines');

INSERT INTO `Airport` (airport_id, airport_name, city, country)
VALUES ('TXL', 'Berlin Tegel', 'Berlin', 'Germany'),
  ('ORD', "Chicago O'Hare International", 'Chicago', 'Illinois'),
  ('ATL', 'Hartsfield-Jackson Atlanta Int', 'Atlanta', 'United States of America'),
  ('TNR', 'Ivato International', 'Antananarivo', 'Madagascar'),
  ('JFK', 'John F. Kennedy International', 'New York', 'United States of America'),
  ('LGA', 'LaGuardia', 'New York', 'United States of America'),
  ('BOS', 'Logan International', 'Boston', 'United States of America'),
  ('LHR', 'London Heathrow', 'London', 'United Kingdom'),
  ('LAX', 'Los Angeles International', 'Los Angeles', 'United States of America'),
  ('SFO', 'San Francisco International', 'San Francisco', 'United States of America'),
  ('NRT', 'Tokyo International', 'Tokyo', 'Japan');

INSERT INTO `Flight` (flight_number, date_of_week, seating_capacity, airline, min_length_stay, max_length_stay) VALUES
  ('111', '1010100', '100', 'AA', 2, 2),
  ('111', '1111111', '150', 'JA', 2, 2),
  ('1337', '0000011', '33', 'AM', 2, 2);

INSERT INTO `Legs` (airline_id, flight_number, leg_number, from_airport, to_airport, departure_time, arrival_time)
VALUES
  ('AA', '111', 1, 'JFK', 'LGA', '2011-01-05 09:00:00', '2011-01-05 11:00:00'),
  ('AA', '111', 2, 'LGA', 'LAX', '2011-01-05 17:00:00', '2011-01-05 19:00:00'),
  ('AA', '111', 3, 'LAX', 'NRT', '2011-01-06 07:30:00', '2011-01-06 10:00:00'),
  ('JA', '111', 1, 'JFK', 'SFO', '2011-01-10 12:00:00', '2011-01-10 14:00:00'),
  ('JA', '111', 2, 'SFO', 'BOS', '2011-01-10 19:30:00', '2011-01-10 22:30:00'),
  ('JA', '111', 3, 'BOS', 'LHR', '2011-01-11 05:00:00', '2011-01-11 08:00:00'),
  ('AM', '1337', 1, 'SFO', 'JFK', '2011-01-13 05:00:00', '2011-01-13 07:00:00'),
  ('AM', '1337', 2, 'JFK', 'TNR', '2011-01-13 23:00:00', '2011-01-14 03:00:00');


INSERT INTO express_way.Person (id, first_name, last_name, address, city, state, zip_code)
VALUES (1, 'Jane', 'Smith', '100 Nicolls Rd', 'Stony Brook', 'NY', 117790);
INSERT INTO express_way.Person (id, first_name, last_name, address, city, state, zip_code)
VALUES (2, 'John', 'Doe', '123 N Fake Street', 'New York', 'NY', 10001);
INSERT INTO express_way.Person (id, first_name, last_name, address, city, state, zip_code)
VALUES (3, 'Rick', 'Astley', '1337 Internet Lane', 'Los Angeles', 'CA', 90001);
INSERT INTO express_way.Person (id, first_name, last_name, address, city, state, zip_code)
VALUES (4, 'Mark', 'Edison', '2333 Hacker Lane', 'Los Angeles', 'CA', 90001);
INSERT INTO express_way.Person (id, first_name, last_name, address, city, state, zip_code)
VALUES (5, 'Peter', 'Vim', '6666 Hacker Lane', 'Los Angeles', 'CA', 90001);

INSERT INTO express_way.user (username, password, role, person_id) VALUES
  ('jsmith', '123', 'user', 1),
  ('jdoe', '123', 'user', 2),
  ('ra', '123', 'user', 3),
  ('medision', '123', 'admin', 4),
  ('pvim', '123', 'employee', 5);

INSERT into express_way.customer (account_number, id, username, credit_card, telephone, email, rating) VALUES
  ('jsmith-1', 1, 'jsmith', '123456778', '1234567', '123@gmail.com', 10),
  ('jdoe-1', 2, 'jdoe', '123456', '123456', '1234@gmail.com', 10),
  ('ra-1', 3, 'ra', '1234', '123456', '123@gmail.com', 10);

INSERT into express_way.employee (ssn, id, username, is_manager, hourly_rate, telephone) VALUES
  ('12345', 4, 'medision', true, 40.0, '109768577'),
  ('15668', 5, 'pvim', false, 20.0, '1233333');

INSERT into express_way.reservations(account_number, total_fare, booking_fee, customer_rep_ssn) VALUES
  ('jsmith-1', 1000.00, 20.0, '12345');

INSERT into express_way.include (reservation_number, airline_id, flight_number, leg_number, passenger_lname, passenger_fname, dept_date, seat_number, class, meal, from_stop_num) VALUES
  (1, 'AA', '111', '1', 'Smith', 'Jane', '2011-01-05 09:00:00', 100, 'economy', 'fish', 1);

INSERT into express_way.reservations(account_number, total_fare, booking_fee, customer_rep_ssn) VALUES
  ('jdoe-1', 800.5, 20, '12345');
INSERT into express_way.include (reservation_number, airline_id, flight_number, leg_number, passenger_lname, passenger_fname, dept_date, seat_number, class, meal, from_stop_num) VALUES
  (2, 'AA', '111', '1', 'Doe', 'John', '2011-01-05 09:00:00', 100, 'economy', 'fish', 1);

INSERT INTO Fare VALUES ("AA", "111", 1, "adult", "economy", 500);
INSERT INTO Fare VALUES ("AA", "111", 2, "adult", "economy", 400);
INSERT INTO Fare VALUES ("AA", "111", 3, "adult", "economy", 600);

INSERT INTO fare VALUES ("JA", '111', 1, 'adult', 'economy', 390);
INSERT INTO fare VALUES ("JA", '111', 2, 'adult', 'economy', 342);
INSERT INTO fare VALUES ("JA", '111', 3, 'adult', 'economy', 190);

INSERT INTO fare VALUES ("AM", '1337', 1, 'adult', 'economy', 749);
INSERT INTO fare VALUES ("AM", '1337', 2, 'adult', 'economy', 389);

INSERT INTO Fare VALUES ("AA", "111", 1, "adult", "business", 800);
INSERT INTO Fare VALUES ("AA", "111", 2, "adult", "business", 999);
INSERT INTO Fare VALUES ("AA", "111", 3, "adult", "business", 888);

INSERT INTO fare VALUES ("JA", '111', 1, 'adult', 'business', 897);
INSERT INTO fare VALUES ("JA", '111', 2, 'adult', 'business', 983);
INSERT INTO fare VALUES ("JA", '111', 3, 'adult', 'business', 1000);

INSERT INTO fare VALUES ("AM", '1337', 1, 'adult', 'business', 897);
INSERT INTO fare VALUES ("AM", '1337', 2, 'adult', 'business', 837);

INSERT INTO Fare VALUES ("AA", "111", 1, "adult", "first", 10000);
INSERT INTO Fare VALUES ("AA", "111", 2, "adult", "first", 11000);
INSERT INTO Fare VALUES ("AA", "111", 3, "adult", "first", 12000);

INSERT INTO fare VALUES ("JA", '111', 1, 'adult', 'first', 13000);
INSERT INTO fare VALUES ("JA", '111', 2, 'adult', 'first', 14000);
INSERT INTO fare VALUES ("JA", '111', 3, 'adult', 'first', 15000);

INSERT INTO fare VALUES ("AM", '1337', 1, 'adult', 'first', 1897);
INSERT INTO fare VALUES ("AM", '1337', 2, 'adult', 'first', 1837);


SELECT * FROM FARE;
SELECT * FROM Reservations;
SELECT * FROM User;
SELECT * FROM Legs;
SELECT * FROM User;


UPDATE express_way.Legs
SET departure_time = "2018-01-05 09:00:00", arrival_time = "2018-01-05 11:00:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "1";

UPDATE express_way.Legs
SET departure_time = "2018-01-05 17:00:00", arrival_time = "2018-01-05 19:00:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "2";

UPDATE express_way.Legs
SET departure_time = "2018-01-06 07:30:00", arrival_time = "2018-01-06 10:00:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "3";

UPDATE express_way.Legs
SET departure_time = "2018-01-10 12:00:00", arrival_time = "2018-01-10 14:00:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "1";

UPDATE express_way.Legs
SET departure_time = "2018-01-10 19:30:00", arrival_time = "2018-01-10 22:30:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "2";

UPDATE express_way.Legs
SET departure_time = "2018-01-11 05:00:00", arrival_time = "2018-01-11 08:00:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "3";

UPDATE express_way.Legs
SET departure_time = "2018-01-13 05:00:00", arrival_time = "2018-01-13 07:00:00"
WHERE airline_id = "AM" AND flight_number = "1337" AND leg_number = "1";

UPDATE express_way.Legs
SET departure_time = "2018-01-13 23:00:00", arrival_time = "2018-01-14 03:00:00"
WHERE airline_id = "AM" AND flight_number = "1337" AND leg_number = "2";



UPDATE express_way.Legs
SET actual_departure_time = "2018-01-05 09:00:00", actual_arrival_time = "2018-01-05 11:00:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "1";

UPDATE express_way.Legs
SET actual_departure_time = "2018-01-05 17:00:00", actual_arrival_time = "2018-01-05 19:00:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "2";

UPDATE express_way.Legs
SET actual_departure_time = "2018-01-06 07:40:00", actual_arrival_time = "2018-01-06 10:10:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "3";

UPDATE express_way.Legs
SET actual_departure_time = "2018-01-10 12:30:00", actual_arrival_time = "2018-01-10 14:30:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "1";

UPDATE express_way.Legs
SET actual_departure_time = "2018-01-10 19:30:00", actual_arrival_time = "2018-01-10 22:30:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "2";

UPDATE express_way.Legs
SET actual_departure_time = "2018-01-11 05:30:00", actual_arrival_time = "2018-01-11 08:30:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "3";

UPDATE express_way.Legs
SET actual_departure_time = "2018-01-13 05:10:00", actual_arrival_time = "2018-01-13 07:10:00"
WHERE airline_id = "AM" AND flight_number = "1337" AND leg_number = "1";

UPDATE express_way.Legs
SET actual_departure_time = "2018-01-13 23:00:00", actual_arrival_time = "2018-01-14 03:00:00"
WHERE airline_id = "AM" AND flight_number = "1337" AND leg_number = "2";

