Use express_way;

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


INSERT INTO express_way.Legs (airline_id, flight_number, leg_number, from_airport, to_airport, departure_time, arrival_time, actual_departure_time, actual_arrival_time) VALUES ('AA', 111, 1, 'JFK', 'ORD', '2017-11-11 15:22:50', '2017-11-11 17:22:59', null, null);
INSERT INTO express_way.Legs (airline_id, flight_number, leg_number, from_airport, to_airport, departure_time, arrival_time, actual_departure_time, actual_arrival_time) VALUES ('AA', 111, 2, 'ORD', 'LHR', '2017-11-12 15:22:50', '2017-11-11 17:22:59', null, null);
INSERT INTO express_way.Legs (airline_id, flight_number, leg_number, from_airport, to_airport, departure_time, arrival_time, actual_departure_time, actual_arrival_time) VALUES ('AA', 111, 3, 'LHR', 'BOS', '2017-11-12 18:22:50', '2017-11-12 20:22:59', null, null);
INSERT INTO express_way.Legs (airline_id, flight_number, leg_number, from_airport, to_airport, departure_time, arrival_time, actual_departure_time, actual_arrival_time) VALUES ('JA', 111, 1, 'JFK', 'LAX', '2017-11-13 08:35:00', '2017-11-13 08:35:03', null, null);
INSERT INTO express_way.Legs (airline_id, flight_number, leg_number, from_airport, to_airport, departure_time, arrival_time, actual_departure_time, actual_arrival_time) VALUES ('AM', 1337, 1, 'ORD', 'LAX', '2017-11-13 12:28:12', '2017-11-13 12:28:14', null, null);


###


INSERT INTO express_way.Fare (airline_id, flight_number, leg_number, fare_type, class, fare) VALUES ('AA', 111, 1, 'adult', 'business', 700.00);
INSERT INTO express_way.Fare (airline_id, flight_number, leg_number, fare_type, class, fare) VALUES ('AA', 111, 1, 'adult', 'economy', 600.00);
INSERT INTO express_way.Fare (airline_id, flight_number, leg_number, fare_type, class, fare) VALUES ('AA', 111, 2, 'adult', 'first', 1200.00);
INSERT INTO express_way.Fare (airline_id, flight_number, leg_number, fare_type, class, fare) VALUES ('AA', 111, 1, 'child', 'first', 1000.00);
INSERT INTO express_way.Fare (airline_id, flight_number, leg_number, fare_type, class, fare) VALUES ('JA', 111, 1, 'adult', 'economy', 800.00);

###



SELECT Legs.airline_id, Legs.flight_number, Legs.leg_number, Legs.from_airport, Legs.to_airport, Legs.departure_time, Legs.to_airport, Fare.fare_type,  Fare.class, Fare.fare
FROM Legs, Fare WHERE DATE(Legs.departure_time) = "2017-11-11" AND from_airport = "JFK" AND to_airport = "ORD" AND Fare.leg_number = Legs.leg_number AND Fare.fare_type = "adult" AND Fare.class = "economy" AND Fare.airline_id = Legs.airline_id;


SELECT * FROM Legs;
SELECT * FROM Fare;



SELECT  (@id :=
        (
        SELECT  from_airport
        FROM    Legs
        WHERE   to_airport = @id
        )) AS flight
FROM    (
        SELECT  @id := "LAX"
        ) vars
STRAIGHT_JOIN
        flight
WHERE   @id IS NOT NULL;



SELECT a.airline_id, a.flight_number, a.leg_number, a.from_airport, a.to_airport, a.departure_time, a.to_airport, f.fare_type,  f.class, f.fare
FROM Legs a, Fare f WHERE a.from_airport = "JFK" AND a.to_airport = "LAX" AND f.flight_number = a.`flight_number` AND f.leg_number = a.leg_number AND f.airline_id = a.airline_id;

SELECT a.from_airport AS "from" , a.to_airport AS "middle", b.to_airport AS "to"
  FROM   Legs a
  JOIN   Legs b ON a.to_airport = b.from_airport
  WHERE  a.from_airport = "JFK" AND b.to_airport = "LAX";

# SELECT a.airline_id, a.flight_number, a.leg_number, a.from_airport, a.to_airport AS "transfer_at", b.to_airport , a.departure_time, a.to_airport, f.fare_type,  f.class, f.fare FROM (Legs a)
SELECT * FROM (Legs a)
  JOIN (Legs b) ON (a.to_airport = b.from_airport)
  JOIN (Fare f) ON (f.airline_id = a.airline_id AND f.fare_type = "adult" AND f.class = "economy")
WHERE b.to_airport = "LAX" AND a.from_airport="JFK";


SELECT a.airline_id, a.flight_number, a.leg_number, a.from_airport, a.to_airport, b.airline_id, b.flight_number, b.leg_number, b.from_airport, b.to_airport, f.fare_type, f.class, f.fare FROM (Legs a)
  JOIN (Legs b) ON (a.to_airport = b.from_airport)
  JOIN (Fare f) ON (f.airline_id = a.airline_id AND f.fare_type = "adult" AND f.class = "economy")
WHERE b.to_airport = "LAX" AND a.from_airport="JFK";






use express_way;
SELECT * FROM User;