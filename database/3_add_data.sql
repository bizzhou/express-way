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


INSERT INTO Customer (id, account_number) VALUES
  (1, '1000001'),
  (2, '1000002'),
  (3, '1000003');

INSERT INTO Passengers (id, account_number, telephone, email) VALUES
  (1, '1000001', '555-555-5555', 'awesomejane@ftw.com'),
  (2, '1000002', '123-123-1234', 'jdoe@woot.com'),
  (3, '1000003', '314-159-2653', 'rickroller@rolld.com');


INSERT INTO express_way.Employee (ssn, id, is_manager, start_date, hourly_rate, telephone)
VALUES (111111111, 4, 0, '2010-10-20 08:47:32', 35.00, '655737373');
INSERT INTO express_way.Employee (ssn, id, is_manager, start_date, hourly_rate, telephone)
VALUES (111111112, 5, 1, '2010-12-20 08:47:32', 25.00, '655733344');


INSERT INTO express_way.Reservations (reservation_number, account_number, reservation_date, total_fare, booking_fee, customer_rep_ssn)
VALUES (444, '1000001', '2011-01-05 09:00:00', 5000.00, 20.00, 111111112);
INSERT INTO express_way.Reservations (reservation_number, account_number, reservation_date, total_fare, booking_fee, customer_rep_ssn)
VALUES (333, '1000003', '2011-01-05 09:00:00', 3333.33, 20.00, 111111112);
INSERT INTO express_way.Reservations (reservation_number, account_number, reservation_date, total_fare, booking_fee, customer_rep_ssn)
VALUES (111, '1000001', '2011-01-05 09:00:00', 1200.00, 20.00, 111111111);
INSERT INTO express_way.Reservations (reservation_number, account_number, reservation_date, total_fare, booking_fee, customer_rep_ssn)
VALUES (222, '1000002', '2011-01-05 09:00:00', 500.00, 20.00, 111111111);



INSERT INTO express_way.Include (reservation_number, airline_id, flight_number, leg_number, passenger_lname, passenger_fname, dept_date, seat_number, class, meal, from_stop_num)
VALUES (111, 'JA', 111, 1, NULL, NULL, '2011-01-14', '13A', 'First', 'Fish and Chips', 2);
INSERT INTO express_way.Include (reservation_number, airline_id, flight_number, leg_number, passenger_lname, passenger_fname, dept_date, seat_number, class, meal, from_stop_num)
VALUES (222, 'AA', 111, 1, NULL, NULL, '2011-01-05', '33F', 'Economy', 'Chips', 1);
INSERT INTO express_way.Include (reservation_number, airline_id, flight_number, leg_number, passenger_lname, passenger_fname, dept_date, seat_number, class, meal, from_stop_num)
VALUES (222, 'AA', 111, 2, NULL, NULL, '2011-01-05', '33F', 'Economy', 'Chips', 2);
INSERT INTO express_way.Include (reservation_number, airline_id, flight_number, leg_number, passenger_lname, passenger_fname, dept_date, seat_number, class, meal, from_stop_num)
VALUES (333, 'AM', 1337, 1, NULL, NULL, '2011-01-13', '1A', 'First', 'Sushi', 1);

INSERT INTO Auctions (account_num, reservation_number, airline_id, flight_num, leg_number, class, dept_date, NYOP, is_accepted)
VALUES
  ('1000001', '111', 'AA', '111', '1', 'First', '2011-01-14 03:00:00', '400', TRUE);

