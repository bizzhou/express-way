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
  ('112', '1111111', '150', 'AA', 2, 2),
  ('113', '0000011', '33', 'AA', 2, 2),
  ('111', '1010100', '100', 'JA', 2, 2),
  ('112', '1111111', '150', 'JA', 2, 2),
  ('113', '0000011', '33', 'JA', 2, 2),
  ('111', '1111111', '150', 'AM', 2, 2),
  ('111', '0000011', '33', 'AM', 2, 2);


INSERT INTO `Legs` (airline_id, flight_number, leg_number, from_airport, to_airport, departure_time, arrival_time) VALUES
  ('AA', '111', 1, 'LAX', 'LGA', '2011-01-05 09:00:00', '2011-01-05 11:00:00'),
  ('AA', '112', 2, 'JFk', 'LAX', '2011-01-05 17:00:00', '2011-01-05 19:00:00'),
  ('AA', '113', 3, 'JFk', 'NRT', '2011-01-06 07:30:00', '2011-01-06 10:00:00'),
  ('JA', '111', 1, 'JFk', 'SFO', '2011-01-10 12:00:00', '2011-01-10 14:00:00'),
  ('JA', '112', 2, 'JFK', 'BOS', '2011-01-10 19:30:00', '2011-01-10 22:30:00'),
  ('JA', '113', 3, 'JFK', 'LHR', '2011-01-11 05:00:00', '2011-01-11 08:00:00'),
  ('AM', '111', 2, 'SFO', 'JFK', '2011-01-13 05:00:00', '2011-01-13 07:00:00'),
  ('AM', '112', 2, 'JFK', 'TNR', '2011-01-13 23:00:00', '2011-01-14 03:00:00');






